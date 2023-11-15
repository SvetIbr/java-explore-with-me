package ru.practicum.service;

import ru.practicum.HitDto;
import ru.practicum.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void add(HitDto hitDto);

    List<ViewStats> get(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
