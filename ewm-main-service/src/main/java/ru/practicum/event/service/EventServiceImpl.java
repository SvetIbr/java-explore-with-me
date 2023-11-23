package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.StatsClient;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.error.exception.CategoryNotFoundException;
import ru.practicum.error.exception.ConflictException;
import ru.practicum.error.exception.EventNotFoundException;
import ru.practicum.error.exception.UserNotFoundException;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.UpdateEventDto;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventStatus;
import ru.practicum.location.mapper.LocationMapper;
import ru.practicum.location.model.Location;
import ru.practicum.location.repository.LocationRepository;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestStatusUpdateDto;
import ru.practicum.request.dto.RequestsResultStatusDto;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;
    private final RequestRepository requestRepository;
    private final LocationMapper locationMapper;
    private final StatsClient statsClient;
    public static final Integer HOURS_BEFORE_EVENT = 2;
    public static final Integer HOUR_BEFORE_EVENT = 1;
    public static final String URI = "/events/";


    public List<EventFullDto> getEventsByParametersForAdmin(List<Long> users,
                                                            List<String> states,
                                                            List<Long> categories,
                                                            String rangeStart,
                                                            String rangeEnd,
                                                            PageRequest of) {
        return null;

    }

    @Transactional
    public EventFullDto updateEventByAdmin(Long eventId, UpdateEventDto updateEventDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException((String
                        .format("Event with id=%d was not found", eventId)),
                        "The required object was not found."));
        event = eventRepository.save(eventMapper.toEvent(updateEventDto, event));
        int views = 0;
        String[] uris = {URI + eventId};

        ResponseEntity<Object> response = statsClient.get(event.getPublishedOn(),
                LocalDateTime.now(), uris, false);
        ArrayList<LinkedHashMap<String, Integer>> stats = (ArrayList<LinkedHashMap<String, Integer>>)response
                .getBody();
        if (stats != null) {
            views = stats.get(0).get("hits");
        }
        return eventMapper.toEventFullDto(event, views);
    }

    @Transactional
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {
        User initiator = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String
                        .format("User with id=%d was not found", userId),
                        "The required object was not found."));
        if (!categoryRepository.existsById(newEventDto.getCategory())) {
            throw new CategoryNotFoundException((String
                    .format("Category with id=%d was not found", newEventDto.getCategory())),
                    "The required object was not found.");
        }
        Event event = eventMapper.toEvent(newEventDto, );
        event.setInitiator(initiator);
        event.setCreatedOn(LocalDateTime.now());
        return eventRepository.save(event);



        Location location = locationRepository.save(locationMapper.toLocation(newEventDto.getLocation()));


        if (event.getEventDate().minusHours(HOURS_BEFORE_EVENT).isBefore(event.getCreatedOn())) {
            throw new ConflictException("Incorrect time input",
                    "The time of event must be at least in 2 hours before published");
        }
        if (Objects.nonNull(event.getPublishedOn())) {
            if (event.getEventDate().minusHours(HOUR_BEFORE_EVENT).isBefore(event.getPublishedOn())) {
                throw new ConflictException("Incorrect time input",
                        "The time of event must be at least in 2 hours before published");
            }
        }
        event = eventRepository.save(event);

        return eventMapper.toEventFullDto(event, 0);
    }

    public List<EventShortDto> getEventsByUser(Long userId, PageRequest pageRequest) {
        List<Event> events = eventRepository.findAllByInitiatorId(userId, pageRequest);
        Map<Long, Integer> views = getStats(events);

        return EventMapper.toShortDtos(events, views);
    }

    public EventFullDto getEventByIdForInitiator(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(String
                                .format("Event with id=%d was not found", eventId),
                        "The required object was not found."));
        int views = 0;

        String[] uris = {URI + eventId};

        ResponseEntity<Object> response = statsClient.get(event.getPublishedOn(),
                LocalDateTime.now(), uris, false);
        ArrayList<LinkedHashMap<String, Integer>> stats = (ArrayList<LinkedHashMap<String, Integer>>)response
                .getBody();
        if (stats != null) {
            views = stats.get(0).get("hits");
        }
        return eventMapper.toEventFullDto(event, views);
    }

    @Transactional
    public EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventDto updateEventDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException((String
                        .format("Event with id=%d was not found", eventId)),
                        "The required object was not found."));

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(String
                    .format("User with id=%d was not found", userId),
                    "The required object was not found.");
        }

        if (EventStatus.PUBLISHED.equals(event.getState())) {
            throw new ConflictException("Incorrect state for updating",
                    "The event can't be published when updating");
        }

        Category category = categoryRepository.findById(updateEventDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException((String
                        .format("Category with id=%d was not found", eventId)),
                        "The required object was not found."));

        Location location = locationRepository.save(locationMapper.toLocation(updateEventDto.getLocation()));

        event = eventMapper.toEvent(event, updateEventDto, category, location);

        if (event.getEventDate().minusHours(HOURS_BEFORE_EVENT).isBefore(event.getCreatedOn())) {
            throw new ConflictException("Incorrect time input",
                    "The time of event must be at least in 2 hours before published");
        }
        if (Objects.nonNull(event.getPublishedOn())) {
            if (event.getEventDate().minusHours(HOUR_BEFORE_EVENT).isBefore(event.getPublishedOn())) {
                throw new ConflictException("Incorrect time input",
                        "The time of event must be at least in 2 hours before published");
            }
        }
        event = eventRepository.save(event);

        int views = 0;
        String[] uris = {URI + eventId};

        ResponseEntity<Object> response = statsClient.get(event.getPublishedOn(),
                LocalDateTime.now(), uris, false);
        ArrayList<LinkedHashMap<String, Integer>> stats = (ArrayList<LinkedHashMap<String, Integer>>)response
                .getBody();
        if (stats != null) {
            views = stats.get(0).get("hits");
        }

        return eventMapper.toEventFullDto(event, views);
    }

    public List<RequestDto> getRequestsByEvent(Long userId, Long eventId) {

        return null;
    }

    @Transactional
    public RequestsResultStatusDto updateStatusEventRequests(Long eventId,
                                                             Long userId,
                                                             RequestStatusUpdateDto requestStatusUpdateDto) {
        return null;
    }

    public List<EventShortDto> getEventsByParametersForUsers(String text,
                                                             List<Long> categories,
                                                             Boolean paid,
                                                             String rangeStart,
                                                             String rangeEnd,
                                                             Boolean onlyAvailable,
                                                             String sort,
                                                             PageRequest of,
                                                             HttpServletRequest request) {

        return null;
    }

    public EventFullDto getEventById(Long id, HttpServletRequest request) {
        return eventRepository.findById(id);
    }
}
