package ru.practicum.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.StatsClient;

import java.time.format.DateTimeFormatter;

import static ru.practicum.constants.Constants.DATE_TIME_FORMAT;

@Configuration
public class Config {

    @Value(value = "${stats.server.url}")
    private String serverUrl;

    @Bean
    public StatsClient getStatsClient() {
        return new StatsClient(serverUrl,new RestTemplateBuilder());
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(DATE_TIME_FORMAT);
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        };
    }
}
