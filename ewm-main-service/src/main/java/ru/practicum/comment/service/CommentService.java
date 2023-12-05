package ru.practicum.comment.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.dto.UpdateCommentDto;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentService {
    List<CommentDto> getAllCommentsByEvent(Long eventId);

    List<CommentDto> searchCommentsForAdmin(String text, List<Long> users, List<Long> events,
                                            boolean onlyUnlocked, boolean onlyBlocked,
                                            LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                            Pageable pageable);
    List<CommentDto> blockComments(List<Long> ids);

    CommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto);

    CommentDto updateComment(Long commentId, Long userId, Long eventId, UpdateCommentDto updateCommentDto);

    List<CommentDto> getAllCommentsForAuthorByEventId(Long userId, Long eventId);

    List<CommentDto> getAllCommentsForAuthor(Long userId);

    void deleteComById(Long userId, Long commentId);

    CommentDto getComById(Long eventId, Long commentId);
}
