package ru.practicum.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.event.model.Event;
import ru.practicum.event.enums.EventState;

import java.util.Optional;
import java.util.Set;

/**
 * Интерфейс хранилища всех событий
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {
    /**
     * Метод получения списка всех событий по списку идентификаторов
     *
     * @param events - список идентификаторов искомых событий
     * @return список объектов Event {@link Event}
     */
    Set<Event> findAllByIdIn(Set<Long> events);

    /**
     * Метод получения события по идентификатору события и идентификатору создателя события
     *
     * @param eventId - идентификатор события
     * @param userId  - идентификатор создателя события
     * @return Optional Event {@link Event}
     */
    Optional<Event> findByIdAndInitiatorId(Long eventId, Long userId);

    /**
     * Метод получения списка всех событий, созданных текущим пользователем
     *
     * @param userId - идентификатор создателя событий
     * @return список объектов Event {@link Event}
     */
    Page<Event> findAllByInitiatorId(Long userId, Pageable pageable);

    /**
     * Метод получения события по идентификатору и состоянию
     *
     * @param id          - идентификатор события
     * @param eventStatus - состояние события
     * @return Optional Event {@link Event}
     */
    Optional<Event> findByIdAndState(Long id, EventState eventStatus);

    /**
     * Метод проверки наличия в хранилище события с определенной категорией
     *
     * @param catId - идентификатор категории
     * @return true, если в хранилище есть события с такой категорией
     *         false, если нет
     */
    boolean existsByCategoryId(Long catId);
}
