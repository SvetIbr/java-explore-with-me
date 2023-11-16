package ru.practicum.service;

import ru.practicum.HitDto;
import ru.practicum.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс сервиса статистики
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
public interface StatsService {
    /**
     * Метод сохранения информации о запросе пользователя в хранилище
     *
     * @param hitDto {@link HitDto}
     */
    void add(HitDto hitDto);

    /**
     * Метод получения информации по посещениям из хранилища
     *
     * @param start  - дата и время начала диапазона, за который нужно выгрузить статистику
     * @param end    - дата и время конца диапазона, за который нужно выгрузить статистику
     * @param uris   - список uri, для которых нужно выгрузить статистику
     * @param unique - нужно ли учитывать только уникальные посещения (только с уникальным ip)
     * @return список объектов {@link ViewStats}
     */
    List<ViewStats> get(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
