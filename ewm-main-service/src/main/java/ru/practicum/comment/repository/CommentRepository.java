package ru.practicum.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.comment.model.Comment;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс хранилища всех комментариев
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
public interface CommentRepository extends JpaRepository<Comment, Long>, QuerydslPredicateExecutor<Comment> {
    /**
     * Метод получения списка всех комментариев, оставленных к конкретному событию
     *
     * @param eventId - идентификатор события
     * @return список объектов Comment {@link Comment}
     */
    List<Comment> findAllByEventId(Long eventId);

    /**
     * Метод получения комментария по идентификатору,
     * оставленного к конкретному событию конкретным автором
     *
     * @param commentId - идентификатор комментария
     * @param userId    - идентификатор автора
     * @param eventId   - идентификатор события
     * @return Optional<Comment> {@link Comment}
     */
    Optional<Comment> findByIdAndAuthorIdAndEventId(Long commentId, Long userId, Long eventId);

    /**
     * Метод получения списка всех комментариев, оставленных конкретным автором к конкретному событию
     *
     * @param eventId - идентификатор события
     * @param userId  - идентификатор автора
     * @return список объектов Comment {@link Comment}
     */
    List<Comment> findAllByEventIdAndAuthorId(Long eventId, Long userId);

    /**
     * Метод получения списка всех комментариев, оставленных конкретным автором
     *
     * @param userId - идентификатор автора
     * @return список объектов Comment {@link Comment}
     */
    List<Comment> findAllByAuthorId(Long userId);

    /**
     * Метод получения комментария по идентификатору, оставленного к конкретному событию
     *
     * @param commentId - идентификатор комментария
     * @param eventId   - идентификатор события
     * @return Optional<Comment> {@link Comment}
     */
    Optional<Comment> findByIdAndEventId(Long commentId, Long eventId);

    /**
     * Метод получения количества комментариев, оставленных к конкретному событию
     *
     * @param id - идентификатор события
     * @return количество комментариев
     */
    @Query(value = "SELECT COUNT(c) " +
            "FROM comments AS c " +
            "WHERE event_id = ?1", nativeQuery = true)
    int findCountCommentByEventId(Long id);

    /**
     * Метод получения комментария по идентификатору, оставленного конкретным автором
     *
     * @param commentId - идентификатор комментария
     * @param userId    - идентификатор автора
     * @return Optional<Comment> {@link Comment}
     */
    Optional<Comment> findByIdAndAuthorId(Long userId, Long commentId);
}
