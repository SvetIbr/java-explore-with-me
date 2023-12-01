package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
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

/**
 * Класс закрытого (доступна только авторизованным пользователям) контроллера
 * для работы с сервисом событий
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventsController {
    /**
     * Поле сервис для работы с хранилищем категорий
     */
    private final EventService eventService;

    /**
     * Метод получения списка всех событий, добавленных текущим пользователем,
     * из хранилища сервиса через запрос
     *
     * @param userId - идентификатор пользователя, запрашивающего список
     * @param from   - индекс первого элемента, начиная с 0
     * @param size   - количество элементов для отображения
     * @return список объектов CategoryDto {@link CategoryDto}
     */
    @GetMapping
    public List<EventShortDto> getEvents(@PathVariable Long userId,
                                         @RequestParam(defaultValue = "0") int from,
                                         @RequestParam(defaultValue = "10") int size) {
        log.info("GET: получение списка событий пользователя с идентификатором {} " +
                "с параметрами: from={} size={}", userId, from, size);
        return eventService.getEventsByUser(userId, PageRequest.of(from / size, size));
    }

    /**
     * Метод добавления события в хранилище сервиса через запрос
     *
     * @param userId      - идентификатор создателя события
     * @param newEventDto {@link NewEventDto}
     * @return {@link EventFullDto} и код ответа API 201
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable Long userId,
                                    @Valid @RequestBody NewEventDto newEventDto) {
        log.info("POST: создание события с параметрами: {} пользователем {}", newEventDto, userId);
        return eventService.createEvent(userId, newEventDto);
    }

    /**
     * Метод получения полной информации о событии, добавленном текущим пользователем,
     * из хранилища сервиса через запрос
     *
     * @param userId  - идентификатор создателя события
     * @param eventId - идентификатор события
     * @return {@link EventFullDto}
     */
    @GetMapping(value = "/{eventId}")
    public EventFullDto getEventByIdForInitiator(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("GET: запрос на событие по идентификатору {} от пользователя {}", eventId, userId);
        return eventService.getEventByIdForInitiator(userId, eventId);
    }

    /**
     * Метод обновления информации о событии, добавленном текущим пользователем
     * в хранилище сервиса через запрос
     *
     * @param userId             - идентификатор создателя события
     * @param eventId            - идентификатор события
     * @param updateEventUserDto {@link UpdateEventDto} - данные для обновления
     * @return копию объекта {@link EventFullDto} с обновленными полями
     */
    @PatchMapping(value = "/{eventId}")
    public EventFullDto updateEvent(@PathVariable Long userId, @PathVariable Long eventId,
                                    @Valid @RequestBody UpdateEventDto updateEventUserDto) {
        log.info("PATCH: обновление события с идентификатором {} пользователем с идентификатором {} " +
                "с параметрами: {}", eventId, userId, updateEventUserDto);
        return eventService.updateEventByUser(userId, eventId, updateEventUserDto);
    }

    /**
     * Метод получения информации о запросах на участие в событии текущего пользователя
     * из хранилища сервиса через запрос
     *
     * @param userId  - идентификатор создателя
     * @param eventId - идентификатор события
     * @return список объектов RequestDto {@link RequestDto}
     */
    @GetMapping(value = "/{eventId}/requests")
    public List<RequestDto> getRequestsByEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("GET: получение списка запросов на участие в событии " +
                "по идентификатору{} пользователем с идентификатором {}", eventId, userId);
        return eventService.getRequestsByEvent(userId, eventId);
    }

    /**
     * Метод изменения статуса заявок на участие (подтверждена, отклонена) в событии текущего пользователя
     * из хранилища сервиса через запрос
     *
     * @param userId                 - идентификатор создателя
     * @param eventId                - идентификатор события
     * @param requestStatusUpdateDto {@link RequestStatusUpdateDto} - данные для обновления
     * @return список объектов RequestDto {@link RequestDto}
     */
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
