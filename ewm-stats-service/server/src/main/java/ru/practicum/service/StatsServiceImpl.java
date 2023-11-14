package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.EndpointHit;
import ru.practicum.ViewStats;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private StatsRepository repository;
    private StatsMapper mapper;

    public void add(EndpointHit endpointHit) {
        repository.save(mapper.toHit(endpointHit));
    }

    public List<ViewStats> get(String start, String end, String[] uris, Boolean unique) {
        LocalDateTime start1 = LocalDateTime.parse(start, formatter);
        LocalDateTime end1 = LocalDateTime.parse(end, formatter);
        if (unique) {
            return repository.getUniqueByIpHits(start1, end1, Arrays.asList(uris));
        } else {
            return repository.getHits(start1, end1, Arrays.asList(uris));
        }
    }
}
