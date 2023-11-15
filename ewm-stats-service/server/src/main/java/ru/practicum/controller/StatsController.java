package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.HitDto;
import ru.practicum.ViewStats;
import ru.practicum.service.StatsService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PostMapping("/hit")
    public ResponseEntity<HttpStatus> postStats(@Valid @RequestBody HitDto hitDto) {
        log.info("POST: Добавление статистики с параметрами: {}", hitDto);
        statsService.add(hitDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/stats")
    public ResponseEntity<List<ViewStats>> getStats(@RequestParam @NotNull String start,
                                    @RequestParam @NotNull String end,
                                    @RequestParam(required = false) List <String> uris,
                                    @RequestParam(defaultValue = "false") boolean unique) {
        log.info("GET: запрос статистики с параметрами: {}, {}, {}, {}", start, end, uris, unique);
        try {
            LocalDateTime start1 = LocalDateTime.parse(start, formatter);
            LocalDateTime end1 = LocalDateTime.parse(end, formatter);
            return  ResponseEntity.ok().body(statsService.get(start1, end1, uris, unique));
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
