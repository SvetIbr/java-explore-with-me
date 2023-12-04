package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.UpdateEventDto;
import ru.practicum.event.service.EventService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.constants.Constants.DATE_TIME_FORMAT;

/**
 * Класс административного (для администраторов сервиса) контроллера для работы с сервисом событий
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
public class AdminEventsController {
    /**
     * Поле сервис для работы с хранилищем категорий
     */
    private final EventService eventService;

    /**
     * Метод получения через запрос списка полной информации о событиях из хранилища сервиса
     * по заданным параметрам
     *
     * @param users      - список id пользователей, чьи события нужно найти
     * @param states     - список состояний, в которых находятся искомые события
     * @param categories - список id категорий, в которых будет вестись поиск
     * @param rangeStart - дата и время, не раньше которых должно произойти событие
     * @param rangeEnd   - дата и время, не позже которых должно произойти событие
     * @param from       - индекс первого элемента, начиная с 0
     * @param size       - количество элементов для отображения
     * @return список EventFullDto {@link EventFullDto}
     */
    @GetMapping
    public List<EventFullDto> getEventsByParameters(@RequestParam(required = false) List<Long> users,
                                                    @RequestParam(required = false) List<String> states,
                                                    @RequestParam(required = false) List<Long> categories,
                                                    @RequestParam(required = false)
                                                    @DateTimeFormat(pattern = DATE_TIME_FORMAT)
                                                    LocalDateTime rangeStart,
                                                    @RequestParam(required = false)
                                                    @DateTimeFormat(pattern = DATE_TIME_FORMAT)
                                                    LocalDateTime rangeEnd,
                                                    @RequestParam(defaultValue = "0") int from,
                                                    @RequestParam(defaultValue = "10") int size) {
        log.info("GET: получение событий по параметрам: users = {}, states = {}," +
                " categories = {}, rangeStart = {}, rangeEnd = {}, " +
                "from = {}, size = {}", users, states, categories, rangeStart, rangeEnd, from, size);
        return eventService.getEventsByParametersForAdmin(users, states, categories, rangeStart, rangeEnd,
                PageRequest.of(from / size, size));

    }

    /**
     * Метод редактирования данных события и его статуса (отклонение или публикация)
     * в хранилище сервиса через запрос
     *
     * @param eventId        - идентификатор категории
     * @param updateEventDto {@link UpdateEventDto} - данные для обновления
     * @return копию объекта EventFullDto {@link EventFullDto} с обновленными полями
     */
    @PatchMapping("/{eventId}")
    public EventFullDto updateEventByAdmin(@PathVariable Long eventId,
                                           @Valid @RequestBody UpdateEventDto updateEventDto) {
        log.info("PATCH: обновление события по идентификатору {} с параметрами: {}", eventId, updateEventDto);
        return eventService.updateEventByAdmin(eventId, updateEventDto);

    }
}
