package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.dto.UpdateCommentDto;
import ru.practicum.comment.service.CommentService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}")
public class PrivateCommentController {
    private final CommentService commentService;

    // получить все свои комментарии
    @GetMapping(value = "/comments")
    public List<CommentDto> getAllCommentsForAuthor(@PathVariable Long userId) {
        log.info("GET: запрос на все оставленные комментарии от пользователя " +
                "идентификатору {}", userId);
        return commentService.getAllCommentsForAuthor(userId);
    }

    // удалить свой комментарий по идентификатору
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/comments/{commentId}")
    public void deleteComById(@PathVariable Long userId, @PathVariable Long commentId) {
        log.info("DELETE: удаление комментария по идентификатору {} от пользователя {}", commentId, userId);
        commentService.deleteComById(userId, commentId);
    }

    //только зарегистрированные пользователи могут оставить коммент и только к опубликованным событиям
    @PostMapping(value = "/events/{eventId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@PathVariable Long userId, @PathVariable Long eventId,
                                 @Valid @RequestBody NewCommentDto newCommentDto) {
        log.info("POST: добавление комментария к событию {} пользователем {} с параметрами {}",
                eventId, userId, newCommentDto);
        return commentService.addComment(userId, eventId, newCommentDto);
    }

    // получить все свои комментарии к конкретному событию
    @GetMapping(value = "/events/{eventId}/comments")
    public List<CommentDto> getAllUserCommentsByEventId(@PathVariable Long eventId, @PathVariable Long userId) {
        log.info("GET: запрос на все комментарии пользователя {}, оставленные к событию {}", userId, eventId);
        return commentService.getAllCommentsForAuthorByEventId(userId, eventId);
    }

    // обновить можно только свой комментарий
    @PatchMapping(value = "/events/{eventId}/comments/{commentId}")
    public CommentDto updateComment(@PathVariable Long userId, @PathVariable Long eventId,
                                    @PathVariable Long commentId,
                                    @Valid @RequestBody UpdateCommentDto updateCommentDto) {
        log.info("PATCH: обновление комментария {} к событию с идентификатором {} " +
                "пользователем с идентификатором {} " +
                "с параметрами: {}", commentId, eventId, userId, updateCommentDto);
        return commentService.updateComment(commentId, userId, eventId, updateCommentDto);
    }
}
