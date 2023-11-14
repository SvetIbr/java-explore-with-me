package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ViewStats;
import ru.practicum.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Hit, Long> {

    @Query("  select h.app as app, h.uri AS uri, count(distinct h.ip) as hits" +
            "    from Hit h " +
            "   where h.timestamp between ?1 and ?2" +
            "     and h.uri in (?3) " +
            "group by h.app, h.uri")
    List<ViewStats> getUniqueByIpHits(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(" select h.app as app, h.uri AS uri, count(h.ip) as hits" +
            "    from Hit h " +
            "   where h.timestamp between ?1 and ?2" +
            "     and h.uri in (?3) " +
            "group by h.app, h.uri")
    List<ViewStats> getHits(LocalDateTime start, LocalDateTime end, List<String> uris);
}
