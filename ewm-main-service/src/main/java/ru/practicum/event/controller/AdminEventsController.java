package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.UpdateEventDto;
import ru.practicum.event.service.EventService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
public class AdminEventsController {
    private final EventService eventService;
    @GetMapping
    public List<EventFullDto> getEventsByParameters(@RequestParam(required = false) List<Long> users,
                                                    @RequestParam(required = false) List<String> states,
                                                    @RequestParam(required = false) List<Long> categories,
                                                    @RequestParam(required = false) String rangeStart,
                                                    @RequestParam(required = false) String rangeEnd,
                                                    @RequestParam(required = false,
                                                            defaultValue = "0") int from,
                                                    @RequestParam(required = false,
                                                            defaultValue = "10") int size) {
        log.info("GET: получение событий по параметрам: users = {}, states = {}," +
                " categories = {}, rangeStart = {}, rangeEnd = {}, " +
                "from = {}, size = {}", users, states, categories, rangeStart, rangeEnd, from, size);
        return eventService.getEventsByParametersForAdmin(users, states, categories, rangeStart, rangeEnd,
                PageRequest.of(from, size));

    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventByAdmin(@PathVariable Long eventId,
                                    @Valid @RequestBody UpdateEventDto updateEventDto) {
        log.info("PATCH: обновление события по идентификатору {} с параметрами: {}", eventId, updateEventDto);
        return eventService.updateEventByAdmin(eventId, updateEventDto);

    }
}
