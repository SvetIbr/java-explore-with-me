package ru.practicum.event.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.error.exception.ConflictException;
import ru.practicum.event.dto.*;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventStatus;
import ru.practicum.location.mapper.LocationMapper;
import ru.practicum.location.model.Location;
import ru.practicum.request.dto.RequestStatus;
import ru.practicum.request.model.Request;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.practicum.error.ErrorHandler.FORMAT;

@Mapper
@RequiredArgsConstructor
public class EventMapper {
    private final LocationMapper locationMapper;
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;
    public Event toEvent(NewEventDto newEventDto, Category category, User initiator, Location location) {
        LocalDateTime current = LocalDateTime.now();
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .category(category)
                .requests(new ArrayList<>())
                .createdOn(current)
                .description(newEventDto.getDescription())
                .eventDate(LocalDateTime.parse(newEventDto.getEventDate(), FORMAT))
                .initiator(initiator)
                .location(location)
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .publishedOn(null)
                .requestModeration(newEventDto.getRequestModeration())
                .state(EventStatus.PENDING)
                .title(newEventDto.getTitle())
                .build();
    }

    public EventFullDto toEventFullDto(Event event, Integer views) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(getConfirmedRequests(event.getRequests()))
                .eventDate(event.getEventDate().format(FORMAT))
                .initiator(userMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(views)
                .createdOn(event.getCreatedOn().format(FORMAT))
                .description(event.getDescription())
                .location(locationMapper.toLocationDto(event.getLocation()))
                .participantLimit(event.getParticipantLimit())
                .publishedOn(Objects.isNull(event.getPublishedOn()) ? null :
                        event.getPublishedOn().format(FORMAT))
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .build();
    }

    public Event toEvent(Event event, UpdateEventDto updateEventDto, Category category,
                                        Location location) {
        String annotation;
        if (updateEventDto.getAnnotation() == null) {
            annotation = event.getAnnotation();
        } else {
            annotation = updateEventDto.getAnnotation();
        }
        EventStatus state = Objects.nonNull(updateEventDto.getStateAction())
                ? getEventStatus(updateEventDto.getStateAction(), event) : event.getState();

        return Event.builder()
                .id(event.getId())
                .annotation(annotation)
                .category(category)
                .requests(event.getRequests())
                .eventDate(Objects.nonNull(updateEventDto.getEventDate())
                ? LocalDateTime.parse(updateEventDto.getEventDate(), FORMAT) : event.getEventDate())
                .initiator(event.getInitiator())
                .paid(Objects.nonNull(updateEventDto.getPaid()) ? updateEventDto.getPaid() : event.getPaid())
                .title(Objects.nonNull(updateEventDto.getTitle())
                        ? updateEventDto.getTitle() : event.getTitle())
                .createdOn(event.getCreatedOn())
                .description(Objects.nonNull(updateEventDto.getDescription())
                        ? updateEventDto.getDescription() : event.getDescription())
                .location(location)
                .participantLimit(Objects.nonNull(updateEventDto.getParticipantLimit())
                        ? updateEventDto.getParticipantLimit() : event.getParticipantLimit())
                .publishedOn(Objects.nonNull(event.getPublishedOn())
                        ? event.getPublishedOn() : Objects.equals(state, EventStatus.PUBLISHED)
                        ? LocalDateTime.now() : null)
                .requestModeration(Objects.nonNull(updateEventDto.getRequestModeration())
                        ? updateEventDto.getRequestModeration() : event.getRequestModeration())
                .state(state)
                .build();
    }

    private Integer getConfirmedRequests(List<Request> requests) {
        int confirmedRequests = 0;
        for (Request request : requests) {
            if (Objects.equals(request.getStatus(), RequestStatus.CONFIRMED))
                confirmedRequests = confirmedRequests + 1;
        }
        return confirmedRequests;
    }

    private static EventStatus getEventStatus(StateAction stateAction, Event event) {
        EventStatus state = null;
        switch (stateAction) {
            case SEND_TO_REVIEW:
                state = EventStatus.PENDING;
                break;
            case CANCEL_REVIEW:
            case REJECT_EVENT:
                checkEventState(event);
                state = EventStatus.CANCELED;
                break;
            case PUBLISH_EVENT:
                checkEventState(event);
                state = EventStatus.PUBLISHED;
                break;
        }
        return state;
    }

    private static void checkEventState(Event event) {
        if (event.getState().equals(EventStatus.PUBLISHED)
                || event.getState().equals(EventStatus.CANCELED)) {
            throw new ConflictException("Impossible to public",
                    "Event is published or canceled");
        }
    }

}
