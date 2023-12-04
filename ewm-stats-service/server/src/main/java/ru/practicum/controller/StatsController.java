package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.HitDto;
import ru.practicum.ViewStats;
import ru.practicum.error.InvalidDateTimeException;
import ru.practicum.service.StatsService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Класс контроллера для работы со статистикой посещений
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {
    /**
     * Поле сервис для работы с хранилищем статистики
     */
    private final StatsService statsService;

    /**
     * Поле класса для для разбора и форматирования даты и времени
     */
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * Метод сохранения информации о том, что на uri конкретного сервиса был отправлен запрос пользователем
     *
     * @param hitDto {@link HitDto}
     */
    @PostMapping("/hit")
    public ResponseEntity<HttpStatus> postStats(@Valid @RequestBody HitDto hitDto) {
        log.info("POST: Добавление статистики с параметрами: {}", hitDto);
        statsService.add(hitDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Метод получения статистики по посещениям
     *
     * @param start  - дата и время начала диапазона, за который нужно выгрузить статистику
     * @param end    - дата и время конца диапазона, за который нужно выгрузить статистику
     * @param uris   - список uri, для которых нужно выгрузить статистику
     * @param unique - нужно ли учитывать только уникальные посещения (только с уникальным ip)
     * @return список объектов {@link ViewStats}
     */
    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam @NotNull String start,
                                    @RequestParam @NotNull String end,
                                    @RequestParam(required = false) List<String> uris,
                                    @RequestParam(defaultValue = "false") boolean unique) {
        log.info("GET: запрос статистики с параметрами: {}, {}, {}, {}", start, end, uris, unique);

        LocalDateTime start1 = LocalDateTime.parse(URLDecoder.decode(start, StandardCharsets.UTF_8), formatter);
        LocalDateTime end1 = LocalDateTime.parse(URLDecoder.decode(end, StandardCharsets.UTF_8), formatter);
        if (end1.isBefore(start1)) {
            throw new InvalidDateTimeException("start after end");
        }
        return statsService.get(start1, end1, uris, unique);
    }
}
