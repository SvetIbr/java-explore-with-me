package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ViewStats;
import ru.practicum.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс хранилища всех запросов по эндпоинтам
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
public interface StatsRepository extends JpaRepository<Hit, Long> {
    /**
     * Метод получения информации по уникальным посещениям (только с уникальным ip)
     * без требования к конкретным uri
     *
     * @param start - дата и время начала диапазона, за который нужно выгрузить статистику
     * @param end   - дата и время конца диапазона, за который нужно выгрузить статистику
     * @return список объектов {@link ViewStats}
     */
    @Query("SELECT new ru.practicum.ViewStats(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<ViewStats> getUniqueHitsWithoutUris(LocalDateTime start, LocalDateTime end);

    /**
     * Метод получения информации по всем посещениям без требования к конкретным uri
     *
     * @param start - дата и время начала диапазона, за который нужно выгрузить статистику
     * @param end   - дата и время конца диапазона, за который нужно выгрузить статистику
     * @return список объектов {@link ViewStats}
     */
    @Query("SELECT new ru.practicum.ViewStats(h.app, h.uri, COUNT(h.ip)) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<ViewStats> getHitsWithoutUris(LocalDateTime start, LocalDateTime end);

    /**
     * Метод получения информации по уникальным посещениям (только с уникальным ip)
     * со списком uri, для которых нужно выгрузить статистику
     *
     * @param start - дата и время начала диапазона, за который нужно выгрузить статистику
     * @param end   - дата и время конца диапазона, за который нужно выгрузить статистику
     * @param uris  - список uri, для которых нужно выгрузить статистику
     * @return список объектов {@link ViewStats}
     */
    @Query("SELECT new ru.practicum.ViewStats(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
            "AND h.uri IN (?3) " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<ViewStats> getUniqueHits(LocalDateTime start, LocalDateTime end, List<String> uris);

    /**
     * Метод получения информации по всем посещениям со списком uri,
     * для которых нужно выгрузить статистику
     *
     * @param start - дата и время начала диапазона, за который нужно выгрузить статистику
     * @param end   - дата и время конца диапазона, за который нужно выгрузить статистику
     * @param uris  - список uri, для которых нужно выгрузить статистику
     * @return список объектов {@link ViewStats}
     */
    @Query("SELECT NEW ru.practicum.ViewStats(h.app, h.uri, COUNT(h.ip)) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
            "AND h.uri IN (?3) " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<ViewStats> getHits(LocalDateTime start, LocalDateTime end, List<String> uris);
}
