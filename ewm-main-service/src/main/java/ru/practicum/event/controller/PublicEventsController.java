package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.enums.EventSort;
import ru.practicum.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс публичного (доступна без регистрации любому пользователю сети) контроллера
 * для работы с сервисом событий
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class PublicEventsController {
    /**
     * Поле сервис для работы с хранилищем категорий
     */
    private final EventService eventService;

    /**
     * Метод получения через запрос списка информации о событиях из хранилища сервиса
     * по заданным параметрам
     *
     * @param text          - текст для поиска в содержимом аннотации и подробном описании события
     * @param paid          - поиск только платных/бесплатных событий
     * @param categories    - список идентификаторов категорий, в которых будет вестись поиск
     * @param rangeStart    - дата и время, не раньше которых должно произойти событие
     * @param rangeEnd      - дата и время, не позже которых должно произойти событие
     * @param onlyAvailable - только события, у которых не исчерпан лимит запросов на участие
     * @param sort          - Вариант сортировки: по дате события или по количеству просмотров
     * @param from          - индекс первого элемента, начиная с 0
     * @param size          - количество элементов для отображения
     * @return список EventShortDto {@link EventShortDto}, подходящих по условия поиска
     */
    @GetMapping
    public List<EventShortDto> getEventsByParameters(@RequestParam(required = false) String text,
                                                     @RequestParam(required = false) List<Long> categories,
                                                     @RequestParam(required = false) Boolean paid,
                                                     @RequestParam(required = false)
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                     LocalDateTime rangeStart,
                                                     @RequestParam(required = false)
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                     LocalDateTime rangeEnd,
                                                     @RequestParam(required = false,
                                                             defaultValue = "false") Boolean onlyAvailable,
                                                     @RequestParam(required = false) EventSort sort,
                                                     @RequestParam(required = false,
                                                             defaultValue = "0") int from,
                                                     @RequestParam(required = false,
                                                             defaultValue = "10") int size,
                                                     HttpServletRequest request) {
        log.info("GET: запрос на получение событий с параметрами: text={}, categories={}, " +
                        "paid={}, rangeStart={}, rangeEnd={}, onlyAvailable={}, sort={}, " +
                        "from={}, size={}", text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
        return eventService.getEventsByParametersForUsers(text, categories, paid, rangeStart,
                rangeEnd, onlyAvailable, sort, PageRequest.of(from / size, size), request);

    }

    /**
     * Метод получения полной информации об опубликованном событии по его идентификатору
     * из хранилища сервиса через запрос
     *
     * @param id - идентификатор события
     * @return {@link EventFullDto}
     */
    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable Long id, HttpServletRequest request) {
        log.info("GET: запрос на получение события по идентификатору {}", id);
        return eventService.getEventById(id, request);
    }
}
