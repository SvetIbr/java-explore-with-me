package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.EndpointHit;
import ru.practicum.ViewStats;
import ru.practicum.service.StatsService;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;
    @PostMapping("/hit")
    public ResponseEntity<HttpStatus> postStats(@RequestBody EndpointHit endpointHit) {
        log.info("POST: Добавление статистики с параметрами: {}", endpointHit);
        statsService.add(endpointHit);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/stats")
    public Collection<ViewStats> getStats(@RequestParam @NotNull String start,
                                          @RequestParam @NotNull String end,
                                          @RequestParam(required = false) String[] uris,
                                          @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        log.info("GET: запрос статистики с параметрами: {}, {}, {}, {}", start, end, uris, unique);
        return  statsService.get(start, end, uris, unique);
    }

}
