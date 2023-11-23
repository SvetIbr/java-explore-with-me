package ru.practicum.event.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findFirstWhereCategoryIdIs(Long catId);

    List<Event> findAllWhereEventIdIn(List<Long> events);

    List<Event> findAllByInitiatorId(Long userId, PageRequest pageRequest);
}
