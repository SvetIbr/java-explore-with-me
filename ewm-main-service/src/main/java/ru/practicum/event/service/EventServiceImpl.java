package ru.practicum.event.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.HitDto;
import ru.practicum.StatsClient;
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
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.constants.Constants.APP_CODE;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMappers eventMapper;
    private final RequestRepository requestRepository;
    private final StatsClient statsClient;
    private final RequestMapper requestMapper;
    private final RequestService requestService;
    private final EventStatService eventStatService;


    // получение событий админом по параметрам
    public List<EventFullDto> getEventsByParametersForAdmin(List<Long> users,
                                                            List<String> states,
                                                            List<Long> categories,
                                                            LocalDateTime rangeStart,
                                                            LocalDateTime rangeEnd,
                                                            Pageable pageable) {
        if ((rangeStart != null && rangeEnd != null)
                && (rangeStart.isAfter(rangeEnd) || rangeStart.isEqual(rangeEnd))) {
            throw new BadRequestException("Start time must not after or equal to end time.");
        }
        List<EventFullDto> events =  getEventsByParameters(users, states, categories,
                rangeStart, rangeEnd, pageable, null,
                null, null, null)
                .stream()
                .map(eventMapper::toEventFullDto)
                .collect(Collectors.toList());
        Map<Long, Long> views = eventStatService.getEventsViews(events.stream()
                .map(EventFullDto::getId)
                .collect(Collectors.toList()));
        return loadViewsToFullEvent(views, events);
    }

    // редактирование события админом, + отклонение или публикация
    @Transactional
    public EventFullDto updateEventByAdmin(Long eventId, UpdateEventDto updateEventDto) {
        Event event = checkAndReturnEventInStorage(eventId);

        event = eventMapper.updateEvent(updateEventDto, event);

        if (updateEventDto.getStateAction() != null) {
            switch (updateEventDto.getStateAction()) {
                case PUBLISH_EVENT:
                    if(updateEventDto.getEventDate() != null) {
                        if (updateEventDto.getEventDate().isBefore(LocalDateTime.now().minusHours(1))) {
                            throw new ConflictException("Дата начала события должна быть не ранее," +
                                    " чем за час от даты публикации");
                        }
                    }
                    if (!event.getState().equals(EventState.PENDING)) {
                        throw new ConflictException("Дата и время события не могут быть раньше," +
                                " чем через два часа от текущего момента");
                    }
                    event = publishEvent(event);
                    break;

                case REJECT_EVENT:
                    if (event.getState().equals(EventState.PUBLISHED)) {
                        throw new ConflictException("Дата и время события не могут быть раньше," +
                                " чем через два часа от текущего момента");
                    }
                    event = cancelEvent(event);
                    break;
            }
        }

        EventFullDto eventFullDto = eventMapper.toEventFullDto(event);
        Map<Long, Long> views = eventStatService.getEventsViews(List.of(eventId));
        eventFullDto.setViews(Math.toIntExact(views.getOrDefault(eventFullDto.getId(), 0L)));
        return eventFullDto;
    }

    @Transactional
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {
        if (newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new InvalidEventStartTimeException("Field: eventDate. " +
                    "Error: должно содержать дату, которая еще не наступила. " +
                    "Value: " + newEventDto.getEventDate());
        }
        Event event = eventMapper.toEvent(newEventDto);
        User initiator = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(String.format("User with id=%d was not found", userId)));
        event.setInitiator(initiator);
        event.setCreatedOn(LocalDateTime.now());
        event = eventRepository.save(event);
        return eventMapper.toEventFullDto(event);
    }

    // получение событий, добавленных текущим пользователем
    public List<EventShortDto> getEventsByUser(Long userId, Pageable pageable) {
        checkUserInStorage(userId);
        Page<Event> events = eventRepository.findAllByInitiatorId(userId, pageable);
        List<EventShortDto> userEvents =  events.stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
        Map<Long, Long> views = eventStatService
                .getEventsViews(userEvents.stream().map(EventShortDto::getId).collect(Collectors.toList()));
        return loadViewsToShortEvent(views, userEvents);
    }

    private List<EventShortDto> loadViewsToShortEvent(Map<Long, Long> views, List<EventShortDto> events) {
        for(EventShortDto cur: events) {
            cur.setViews(Math.toIntExact(views.getOrDefault(cur.getId(), 0L)));
        }
        return events;
    }

    // получение полной информации о событии, добавленном текущим пользователем
    public EventFullDto getEventByIdForInitiator(Long userId, Long eventId) {
        checkUserInStorage(userId);

        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException(String.format("Event " +
                        "with id=%d was not found", eventId)));
        Map<Long, Long> views = eventStatService.getEventsViews(List.of(eventId));

        EventFullDto eventFullDto = eventMapper.toEventFullDto(event);
        eventFullDto.setViews(Math.toIntExact(views.getOrDefault(eventFullDto.getId(), 0L)));

        return eventFullDto;
    }

    // изменение события, добавленного текущим пользователем
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
                    throw new BadRequestException("Unexpected value: " + stateAction);
            }
        }

        event = eventMapper.updateEvent(updateEventDto, event);
        Map<Long, Long> views = eventStatService.getEventsViews(List.of(eventId));
        EventFullDto eventFullDto = eventMapper.toEventFullDto(eventRepository.save(event));
        eventFullDto.setViews(Math.toIntExact(views.getOrDefault(eventFullDto.getId(), 0L)));
        return eventFullDto;
    }

    // получение информации о запросах на участие в событии текущего пользователя
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

    // изменение статуса заявок на участие в событии текущего пользователя
    @Transactional
    public RequestsResultStatusDto updateStatusEventRequests(Long eventId,
                                                             Long userId,
                                                             RequestStatusUpdateDto requestStatusUpdateDto) {

        checkUserInStorage(userId);

        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId).orElseThrow(
                () -> new NotFoundException(String.format("Event with id=%d was not found", eventId)));

        return requestService.updateRequestsStatusByEvent(requestStatusUpdateDto, event);
    }

    // получение событий по параметрам для всех пользователей
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
            throw new BadRequestException("Start time must not after or equal to end time.");
        }

        List<Event> events = getEventsByParameters(null, null, categories, rangeStart,
                rangeEnd, pageable, text, paid, onlyAvailable, EventState.PUBLISHED);

        statsClient.post(createHitDto(request));

        List<EventShortDto> eventShortDtos = events.stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());

        if (sort != null) {
            sortEvents(eventShortDtos, sort);
        }

        Map<Long, Long> views = eventStatService.getEventsViews(events.stream()
                .map(Event::getId).collect(Collectors.toList()));

        return loadViewsToShortEvent(views, eventShortDtos);
    }

    private HitDto createHitDto(HttpServletRequest request) {
        return HitDto.builder()
                .app(APP_CODE)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
    }

    // получение подробной информации о событии по идентификатору для любого пользователя - только опубликованные!
    public EventFullDto getEventById(Long id, HttpServletRequest request) {
        Event event = eventRepository.findByIdAndState(id, EventState.PUBLISHED).orElseThrow(
                () -> new NotFoundException(String.format("Event with id=%d was not found", id)));

        EventFullDto eventFullDto = eventMapper.toEventFullDto(eventRepository.save(event));
        statsClient.post(createHitDto(request));
        Map<Long, Long> views = eventStatService.getEventsViews(List.of(id));
        eventFullDto.setViews(Math.toIntExact(views.getOrDefault(eventFullDto.getId(), 0L)));
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
            throw new AccessException(String.format("User with id=%d was unavailable for you", userId));
        }
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Event must not be published");
        }
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ConflictException("You can edit an event no later than two hours before the start");
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
            throw new NotFoundException(String.format("User with id=%d was not found", userId));
        }
    }

    private Event checkAndReturnEventInStorage(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException(String.format("Event with id=%d was not found", eventId)));
    }

    private void checkEventInStorage(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new NotFoundException(String.format("Event with id=%d was not found", eventId));
        }
    }

    private List<EventFullDto> loadViewsToFullEvent(Map<Long, Long> views, List<EventFullDto> events) {
        for(EventFullDto cur: events) {
            cur.setViews(Math.toIntExact(views.getOrDefault(cur.getId(), 0L)));
        }
        return events;
    }
}
