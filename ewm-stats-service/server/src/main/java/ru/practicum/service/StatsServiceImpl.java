package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.HitDto;
import ru.practicum.ViewStats;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;
    private final StatsMapper mapper;

    public void add(HitDto hitDto) {
        repository.save(mapper.toHit(hitDto));
        log.info("Сохранение новой записи статистики");
    }

    public List<ViewStats> get(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (uris == null || uris.isEmpty()) {
            if (unique) {
                return repository.getUniqueHitsWithoutUris(start, end);
            } else {
                return repository.getHitsWithoutUris(start, end);
            }
        }

        if (unique) {
            return repository.getUniqueHits(start, end, uris);
        } else {
            return repository.getHits(start, end, uris);
        }
    }
}

