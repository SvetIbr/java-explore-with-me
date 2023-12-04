package ru.practicum.event.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.practicum.HitDto;
import ru.practicum.StatsClient;
import ru.practicum.ViewStats;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.practicum.constants.Constants.APP_CODE;
import static ru.practicum.constants.Constants.URI;

/**
 * Класс статистики просмотров событий
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EventStatService {
    private final StatsClient statsClient;
    private final ObjectMapper objectMapper;
    private final Gson gson;

    /**
     * Метод получения просмотров событий из списка идентификаторов
     *
     * @param events - список id событий, чьи просмотры запрашиваются
     * @return карту просмотров каждого события (ключ - идентификатор события,
     * значение - количество просмотров)
     */
    public Map<Long, Long> getEventsViews(List<Long> events) {
        List<ViewStats> stats;
        Map<Long, Long> views = new HashMap<>();
        List<String> uris = new ArrayList<>();

        if (events == null || events.isEmpty()) {
            return views;
        }
        for (Long id : events) {
            uris.add(URI + id);
        }
        ResponseEntity<Object> response = statsClient.get(LocalDateTime.now().minusDays(100),
                LocalDateTime.now(), uris, true);
        Object body = response.getBody();
        if (body != null) {
            String json = gson.toJson(body);
            TypeReference<List<ViewStats>> typeRef = new TypeReference<>() {
            };
            try {
                stats = objectMapper.readValue(json, typeRef);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Ошибка при загрузке данных из сервиса статистики");
            }

            if (!stats.isEmpty()) {
                for (ViewStats stat : stats) {
                    views.put(Long.parseLong(stat.getUri().split("/", 0)[2]),
                            stat.getViews());
                }
            }
        }
        return views;
    }


    /**
     * Метод сохранения данных запроса в хранилище статистики просмотров
     */
    public void sendHit(HttpServletRequest request) {
        statsClient.post(createHitDto(request));
    }

    /**
     * Метод получения нового объекта HitDto из данных запроса
     *
     * @return HitDto {@link HitDto}
     */
    private HitDto createHitDto(HttpServletRequest request) {
        return HitDto.builder()
                .app(APP_CODE)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
