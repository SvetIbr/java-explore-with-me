package ru.practicum.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.comment.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, QuerydslPredicateExecutor<Comment> {
    List<Comment> findAllByEventId(Long eventId);

    Optional<Comment> findByIdAndAuthorIdAndEventId(Long commentId, Long userId, Long eventId);

    List<Comment> findAllByEventIdAndAuthorId(Long eventId, Long userId);

    List<Comment> findAllByAuthorId(Long userId);

    Optional<Comment> findByIdAndEventId(Long commentId, Long eventId);
    @Query(value = "SELECT COUNT(c) " +
            "FROM comments AS c " +
            "WHERE event_id = ?1", nativeQuery = true)
    int findCountCommentByEventId(Long id);

    Optional<Comment> findByIdAndAuthorId(Long userId, Long commentId);
}
