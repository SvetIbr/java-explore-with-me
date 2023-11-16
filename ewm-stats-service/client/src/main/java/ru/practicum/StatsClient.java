package ru.practicum;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Класс  реализации основных REST-методов с использованием RestTemplate
 * для взаимодействия с основным сервисом приложения
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
public class StatsClient extends BaseClient {
    private static final String PREFIX_STATS = "/stats";
    private static final String PREFIX_HIT = "/hit";

    @Value("${format.pattern.datetime}")
    public String dateTimeFormat;

    public StatsClient(@Value("${stats.server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> post(HitDto hitDto) {
        return post(PREFIX_HIT, hitDto);
    }

    public ResponseEntity<Object> get(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", URLEncoder.encode(start.format(DateTimeFormatter.ofPattern(dateTimeFormat)),
                        StandardCharsets.UTF_8),
                "end", URLEncoder.encode(end.format(DateTimeFormatter.ofPattern(dateTimeFormat)),
                        StandardCharsets.UTF_8),
                "uris", uris,
                "unique", unique);
        return get(PREFIX_STATS + "?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }
}