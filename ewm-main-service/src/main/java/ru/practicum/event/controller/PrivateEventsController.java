package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.UpdateEventDto;
import ru.practicum.event.service.EventService;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestStatusUpdateDto;
import ru.practicum.request.dto.RequestsResultStatusDto;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventsController {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getEvents(@PathVariable Long userId,
                                         @RequestParam(required = false, defaultValue = "0") int from,
                                         @RequestParam(required = false, defaultValue = "10") int size) {
        log.info("GET: получение списка событий пользователя с идентификатором {} " +
                "с параметрами: from={} size={}", userId, from, size);
        return eventService.getEventsByUser(userId, PageRequest.of(from, size));

    }

    @PostMapping
    public EventFullDto createEvent(@PathVariable Long userId,
                                    @Valid @RequestBody NewEventDto newEventDto) {
        log.info("POST: создание события с параметрами: {} пользователем {}", newEventDto, userId);
        return eventService.createEvent(userId, newEventDto);

    }

    @GetMapping(value = "/{eventId}")
    public EventFullDto getEventByIdForInitiator(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("GET: запрос на событие по идентиикатору {} от пользователя {}", eventId, userId);
        return eventService.getEventByIdForInitiator(userId, eventId);
    }

    @PatchMapping(value = "/{eventId}")
    public EventFullDto updateEvent(@PathVariable Long userId, @PathVariable Long eventId,
                                    @Valid @RequestBody UpdateEventDto updateEventUserDto) {
        log.info("PATCH: обновление события с идентификатором {} пользователем с идентификатором {} " +
                "с параметрами: {}", eventId, userId, updateEventUserDto);
        return eventService.updateEventByUser(userId, eventId, updateEventUserDto);
    }

    @GetMapping(value = "/{eventId}/requests")
    public List<RequestDto> getRequestsByEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("GET: получение списка запросов на участие в событии " +
                "по идентификатору{} пользователем с идентификатором {}", eventId, userId);
        return eventService.getRequestsByEvent(userId, eventId);
    }

    @PatchMapping(value = "/{eventId}/requests")
    public RequestsResultStatusDto updateStatusRequests(@PathVariable Long userId,
                                                        @PathVariable Long eventId,
                                                        @Valid @RequestBody RequestStatusUpdateDto
                                                                requestStatusUpdateDto) {
        log.info("PATCH: обновление от пользователя с идентификатором {} " +
                "для события с идентификатором {} " +
                "с параметрами: {}", userId, eventId, requestStatusUpdateDto);
        return eventService.updateStatusEventRequests(eventId, userId, requestStatusUpdateDto);
    }

}
