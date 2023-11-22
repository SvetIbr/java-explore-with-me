package ru.practicum.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.StatsClient;

@Configuration
public class StatsClientConfig {

    private RestTemplateBuilder builder;

    @Value("${stats.server.url}")
    private String serverUrl;

    @Bean
    public StatsClient getStatsClient() {
        return new StatsClient(serverUrl,builder);
    }
}
