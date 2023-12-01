package ru.practicum.event.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.UpdateEventDto;
import ru.practicum.event.enums.EventSort;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestStatusUpdateDto;
import ru.practicum.request.dto.RequestsResultStatusDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventFullDto> getEventsByParametersForAdmin(List<Long> users, List<String> states,
                                                     List<Long> categories, LocalDateTime rangeStart,
                                                     LocalDateTime rangeEnd, Pageable pageable);

    EventFullDto updateEventByAdmin(Long eventId, UpdateEventDto updateEventDto);

    EventFullDto createEvent(Long userId, NewEventDto newEventDto);

    List<EventShortDto> getEventsByUser(Long userId, Pageable pageable);

    EventFullDto getEventByIdForInitiator(Long userId, Long eventId);

    EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventDto updateEventUserDto);

    List<RequestDto> getRequestsByEvent(Long userId, Long eventId);

    RequestsResultStatusDto updateStatusEventRequests(Long eventId, Long userId,
                                                      RequestStatusUpdateDto requestStatusUpdateDto);

    List<EventShortDto> getEventsByParametersForUsers(String text, List<Long> categories,
                                                      Boolean paid, LocalDateTime rangeStart,
                                                      LocalDateTime rangeEnd, Boolean onlyAvailable,
                                                      EventSort sort, Pageable pageable,
                                                      HttpServletRequest request);

    EventFullDto getEventById(Long id, HttpServletRequest request);
}
