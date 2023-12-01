package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.request.model.Request;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс хранилища всех заявок на участие
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
public interface RequestRepository extends JpaRepository<Request, Long> {
    /**
     * Метод получения списка всех заявок на участие текущего пользователя
     *
     * @param userId - идентификатор текущего пользователя
     * @return список объектов Request {@link Request}
     */
    List<Request> findAllByRequesterId(Long userId);

    /**
     * Метод получения заявки на участие текущего пользователя по идентификатору
     *
     * @param requestId - идентификатор заявки
     * @param userId    - идентификатор пользователя
     * @return Optional Request {@link Request}
     */
    Optional<Request> findByIdAndRequesterId(Long requestId, Long userId);

    /**
     * Метод получения списка всех заявок на участие в конкретном событии,
     * созданном конкретным пользователем
     *
     * @param eventId - идентификатор события
     * @param userId  - идентификатор пользователя
     * @return список объектов Request {@link Request}
     */
    List<Request> getAllByEventIdAndEventInitiatorId(Long eventId, Long userId);

    /**
     * Метод получения списка всех заявок на участие по идентификаторам в конкретном событии
     *
     * @param requestIds - список идентификаторов заявок
     * @param id         - идентификатор события
     * @return список объектов Request {@link Request}
     */
    List<Request> findAllByIdInAndEventId(List<Long> requestIds, Long id);

    /**
     * Метод проверки наличия в хранилище заявки на участие текущего пользователя в конкретном событии
     *
     * @param userId  - идентификатор текущего пользователя
     * @param eventId - идентификатор события
     * @return true, если на участие в событии подана заявка от текущего пользователя,
     *         false, если нет такой заявки
     */
    boolean existsByRequesterIdAndEventId(Long userId, Long eventId);
}
