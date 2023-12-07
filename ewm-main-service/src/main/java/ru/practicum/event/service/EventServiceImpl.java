package ru.practicum.event.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.error.exception.*;
import ru.practicum.event.dto.*;
import ru.practicum.event.enums.EventSort;
import ru.practicum.event.mapper.EventMappers;
import ru.practicum.event.enums.EventState;
import ru.practicum.event.model.QEvent;
import ru.practicum.event.enums.StateAction;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.model.Event;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestStatusUpdateDto;
import ru.practicum.request.dto.RequestsResultStatusDto;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.model.Request;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.request.service.RequestService;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.constants.Constants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMappers eventMapper;
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final RequestService requestService;
    private final EventStatService eventStatService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    public List<EventFullDto> getEventsByParametersForAdmin(List<Long> users,
                                                            List<String> states,
                                                            List<Long> categories,
                                                            LocalDateTime rangeStart,
                                                            LocalDateTime rangeEnd,
                                                            Pageable pageable) {
        if ((rangeStart != null && rangeEnd != null)
                && (rangeStart.isAfter(rangeEnd) || rangeStart.isEqual(rangeEnd))) {
            throw new BadRequestException(START_AFTER_END_MSG);
        }
        List<EventFullDto> events = getEventsByParameters(users, states, categories,
                rangeStart, rangeEnd, pageable, null,
                null, null, null)
                .stream()
                .map(eventMapper::toEventFullDto)
                .collect(Collectors.toList());

        Map<Long, Long> views = eventStatService.getEventsViews(events.stream()
                .map(EventFullDto::getId)
                .collect(Collectors.toList()));

        return loadViewsAndCommentsToFullEvents(views, events);
    }

    @Transactional
    public EventFullDto updateEventByAdmin(Long eventId, UpdateEventDto updateEventDto) {
        Event event = checkAndReturnEventInStorage(eventId);

        event = eventMapper.updateEvent(updateEventDto, event);

        if (updateEventDto.getStateAction() != null) {
            switch (updateEventDto.getStateAction()) {
                case PUBLISH_EVENT:
                    if (updateEventDto.getEventDate() != null) {
                        if (updateEventDto.getEventDate().isBefore(LocalDateTime.now()
                                .minusHours(HOUR_BEFORE_EVENT_DATE))) {
                            throw new ConflictException(START_EVENT_DATE_IN_LESS_THAN_ONE_HOUR_ADMIN_MSG);
                        }
                    }
                    if (!event.getState().equals(EventState.PENDING)) {
                        throw new ConflictException(INCORRECT_STATUS_MSG);
                    }
                    event = publishEvent(event);
                    break;

                case REJECT_EVENT:
                    if (event.getState().equals(EventState.PUBLISHED)) {
                        throw new ConflictException(NOT_PUBLIC_EVENT_MSG);
                    }
                    event = cancelEvent(event);
                    break;
                default:
                    throw new BadRequestException(String.format(UNEXPECTED_VALUE_MSG,
                            updateEventDto.getStateAction()));
            }
        }

        EventFullDto eventFullDto = eventMapper.toEventFullDto(event);

        loadViewsAndCommentsToFullEvent(eventFullDto);

        return eventFullDto;
    }

    @Transactional
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {
        if (newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(HOURS_BEFORE_EVENT_DATE))) {
            throw new InvalidEventStartTimeException(String.format(INCORRECT_EVENT_DATE_MSG,
                    newEventDto.getEventDate()));
        }
        Event event = eventMapper.toEvent(newEventDto);

        User initiator = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));

        event.setInitiator(initiator);
        event.setCreatedOn(LocalDateTime.now());
        event = eventRepository.save(event);
        EventFullDto eventFullDto = eventMapper.toEventFullDto(event);
        eventFullDto.setComments(new ArrayList<>());

        return eventFullDto;
    }

    @Transactional(readOnly = true)
    public List<EventShortDto> getEventsByUser(Long userId, Pageable pageable) {
        checkUserInStorage(userId);

        Page<Event> events = eventRepository.findAllByInitiatorId(userId, pageable);

        List<EventShortDto> userEvents = events.stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());

        Map<Long, Long> views = eventStatService.getEventsViews(userEvents
                .stream()
                .map(EventShortDto::getId)
                .collect(Collectors.toList()));

        return loadViewsAndCommentsToShortEvents(views, userEvents);
    }

    @Transactional(readOnly = true)
    public EventFullDto getEventByIdForInitiator(Long userId, Long eventId) {
        checkUserInStorage(userId);

        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException(String.format(EVENT_NOT_FOUND_MSG, eventId)));

        Map<Long, Long> views = eventStatService.getEventsViews(List.of(eventId));
        EventFullDto eventFullDto = eventMapper.toEventFullDto(event);
        eventFullDto.setViews(Math.toIntExact(views.getOrDefault(eventFullDto.getId(), 0L)));
        eventFullDto.setComments(commentRepository.findAllByEventId(eventFullDto.getId())
                .stream().map(commentMapper::toCommentShortDto).collect(Collectors.toList()));

        return eventFullDto;
    }

    @Transactional
    public EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventDto updateEventDto) {
        checkUserInStorage(userId);

        Event event = checkAndReturnEventInStorage(eventId);

        checkBeforeUpdateUser(event, userId);

        if (updateEventDto.getStateAction() != null) {
            StateAction stateAction = updateEventDto.getStateAction();
            switch (stateAction) {
                case CANCEL_REVIEW:
                    event.setState(EventState.CANCELED);
                    break;
                case SEND_TO_REVIEW:
                    event.setState(EventState.PENDING);
                    break;
                default:
                    throw new BadRequestException(String.format(UNEXPECTED_VALUE_MSG, stateAction));
            }
        }

        event = eventMapper.updateEvent(updateEventDto, event);
        Map<Long, Long> views = eventStatService.getEventsViews(List.of(eventId));
        EventFullDto eventFullDto = eventMapper.toEventFullDto(eventRepository.save(event));
        eventFullDto.setViews(Math.toIntExact(views.getOrDefault(eventFullDto.getId(), 0L)));
        eventFullDto.setComments(commentRepository.findAllByEventId(eventFullDto.getId())
                .stream().map(commentMapper::toCommentShortDto).collect(Collectors.toList()));

        return eventFullDto;
    }

    @Transactional(readOnly = true)
    public List<RequestDto> getRequestsByEvent(Long userId, Long eventId) {
        checkUserInStorage(userId);

        checkEventInStorage(eventId);

        List<Request> requests = requestRepository.getAllByEventIdAndEventInitiatorId(eventId, userId);
        if (requests.isEmpty()) {
            return new ArrayList<>();
        } else {
            return requests.stream()
                    .map(requestMapper::toRequestDto)
                    .collect(Collectors.toList());
        }
    }

    @Transactional
    public RequestsResultStatusDto updateStatusEventRequests(Long eventId,
                                                             Long userId,
                                                             RequestStatusUpdateDto
                                                                     requestStatusUpdateDto) {

        checkUserInStorage(userId);

        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId).orElseThrow(
                () -> new NotFoundException(String.format(EVENT_NOT_FOUND_MSG, eventId)));

        return requestService.updateRequestsStatusByEvent(requestStatusUpdateDto, event);
    }

    @Transactional(readOnly = true)
    public List<EventShortDto> getEventsByParametersForUsers(String text,
                                                             List<Long> categories,
                                                             Boolean paid,
                                                             LocalDateTime rangeStart,
                                                             LocalDateTime rangeEnd,
                                                             Boolean onlyAvailable,
                                                             EventSort sort,
                                                             Pageable pageable,
                                                             HttpServletRequest request) {

        if ((rangeStart != null && rangeEnd != null)
                && (rangeStart.isAfter(rangeEnd)
                || rangeStart.isEqual(rangeEnd))) {
            throw new BadRequestException(START_AFTER_END_MSG);
        }

        List<Event> events = getEventsByParameters(null, null, categories, rangeStart,
                rangeEnd, pageable, text, paid, onlyAvailable, EventState.PUBLISHED);

        eventStatService.sendHit(request);

        List<EventShortDto> eventShortDtos = events.stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());

        if (sort != null) {
            sortEvents(eventShortDtos, sort);
        }

        Map<Long, Long> views = eventStatService.getEventsViews(events.stream()
                .map(Event::getId).collect(Collectors.toList()));

        return loadViewsAndCommentsToShortEvents(views, eventShortDtos);
    }

    @Transactional(readOnly = true)
    public EventFullDto getEventById(Long id, HttpServletRequest request) {
        Event event = eventRepository.findByIdAndState(id, EventState.PUBLISHED).orElseThrow(
                () -> new NotFoundException(String.format(EVENT_NOT_FOUND_MSG, id)));

        EventFullDto eventFullDto = eventMapper.toEventFullDto(eventRepository.save(event));

        eventStatService.sendHit(request);

        loadViewsAndCommentsToFullEvent(eventFullDto);

        return eventFullDto;
    }

    private List<Event> getEventsByParameters(List<Long> users,
                                              List<String> states,
                                              List<Long> categories,
                                              LocalDateTime rangeStart,
                                              LocalDateTime rangeEnd,
                                              Pageable pageable,
                                              String text,
                                              Boolean paid,
                                              Boolean onlyAvailable,
                                              EventState status) {
        QEvent qEvent = QEvent.event;
        BooleanBuilder predicate = new BooleanBuilder();

        if (users != null && !users.isEmpty()) {
            predicate.and(qEvent.initiator.id.in(users));
        }
        if (states != null && !states.isEmpty()) {
            predicate.and(qEvent.state.in(states.stream()
                    .map(s -> EventState.valueOf(s.toUpperCase()))
                    .collect(Collectors.toList())));
        }
        if (categories != null && !categories.isEmpty()) {
            predicate.and(qEvent.category.id.in(categories));
        }

        if (rangeStart != null) {
            predicate.and(qEvent.eventDate.goe(rangeStart));
        }

        if (rangeEnd != null) {
            predicate.and(qEvent.eventDate.loe(rangeEnd));
        }

        if (rangeEnd == null && rangeStart == null) {
            predicate.and(qEvent.eventDate.goe(LocalDateTime.now()));
        }

        if (status != null) {
            predicate.and(qEvent.state.eq(status));
        }

        if (text != null && !text.isEmpty()) {
            predicate.and(qEvent.annotation.containsIgnoreCase(text))
                    .or(qEvent.description.containsIgnoreCase(text));
        }

        if (paid != null) {
            predicate.and(qEvent.paid.eq(paid));
        }

        if (onlyAvailable != null) {
            if (onlyAvailable) {
                predicate.and(qEvent.confirmedRequests.loe(qEvent.participantLimit));
            }
        }

        return eventRepository.findAll(predicate, pageable).toList();
    }

    private Event cancelEvent(Event event) {
        event.setState(EventState.CANCELED);
        return eventRepository.save(event);
    }

    private Event publishEvent(Event event) {
        event.setPublishedOn(LocalDateTime.now());
        event.setState(EventState.PUBLISHED);
        return eventRepository.save(event);
    }

    private void checkBeforeUpdateUser(Event event, Long userId) {
        if (!event.getInitiator().getId().equals(userId)) {
            throw new AccessException(UNAVAILABLE_EVENT_MSG);
        }
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException(NOT_FOR_PUBLISH_EVENT_MSG);
        }
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(HOURS_BEFORE_EVENT_DATE))) {
            throw new ConflictException(START_EVENT_DATE_IN_LESS_THAN_TWO_HOURS_MSG);
        }
    }

    private void sortEvents(List<EventShortDto> eventShortDtos, EventSort sort) {
        if (sort.equals(EventSort.EVENT_DATE)) {
            eventShortDtos.sort(Comparator.comparing(EventShortDto::getEventDate));
        }
        if (EventSort.VIEWS.equals(sort)) {
            eventShortDtos.sort(Comparator.comparing(EventShortDto::getViews));
        }
    }

    private void checkUserInStorage(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format(USER_NOT_FOUND_MSG, userId));
        }
    }

    private Event checkAndReturnEventInStorage(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException(String.format(EVENT_NOT_FOUND_MSG, eventId)));
    }

    private void checkEventInStorage(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new NotFoundException(String.format(EVENT_NOT_FOUND_MSG, eventId));
        }
    }

    private List<EventFullDto> loadViewsAndCommentsToFullEvents(Map<Long, Long> views,
                                                                List<EventFullDto> events) {
        for (EventFullDto cur : events) {
            cur.setViews(Math.toIntExact(views.getOrDefault(cur.getId(), 0L)));
            cur.setComments(commentRepository.findAllByEventId(cur.getId())
                    .stream().map(commentMapper::toCommentShortDto)
                    .filter(comment -> comment.getIsBlocked() == null || !comment.getIsBlocked())
                    .collect(Collectors.toList()));
        }
        return events;
    }

    private List<EventShortDto> loadViewsAndCommentsToShortEvents(Map<Long, Long> views,
                                                                  List<EventShortDto> events) {
        for (EventShortDto cur : events) {
            cur.setViews(Math.toIntExact(views.getOrDefault(cur.getId(), 0L)));
            cur.setComments(commentRepository.findCountCommentByEventId(cur.getId()));
        }
        return events;
    }

    private void loadViewsAndCommentsToFullEvent(EventFullDto eventFullDto) {
        Map<Long, Long> views = eventStatService.getEventsViews(List.of(eventFullDto.getId()));
        eventFullDto.setViews(Math.toIntExact(views.getOrDefault(eventFullDto.getId(), 0L)));
        eventFullDto.setComments(commentRepository.findAllByEventId(eventFullDto.getId())
                .stream().map(commentMapper::toCommentShortDto)
                .filter(comment -> comment.getIsBlocked() == null || !comment.getIsBlocked())
                .collect(Collectors.toList()));
    }
}
