package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class PublicEventsController {
    private final EventService eventService;
    @GetMapping
    public List<EventShortDto> getEventsByParameters(@RequestParam(required = false) String text,
                                                     @RequestParam(required = false) List<Long> categories,
                                                     @RequestParam(required = false) Boolean paid,
                                                     @RequestParam(required = false) String rangeStart,
                                                     @RequestParam(required = false) String rangeEnd,
                                                     @RequestParam(required = false,
                                                             defaultValue = "false") Boolean onlyAvailable,
                                                     @RequestParam(required = false) String sort,
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
                rangeEnd, onlyAvailable, sort, PageRequest.of(from, size), request);

    }

    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable Long id, HttpServletRequest request) {
        log.info("GET: запрос на получение события по идентификатору {}", id);
        return eventService.getEventById(id, request);
    }
}
