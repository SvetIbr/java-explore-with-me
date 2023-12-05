package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.service.CommentService;

import java.util.List;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "events/{eventId}/comments")
public class PublicCommentController {
    private final CommentService commentService;

    // увидеть все комментарии к событию - только опубликованные события
    @GetMapping
    public List<CommentDto> getAllCommentsByEvent(@PathVariable Long eventId) {
        log.info("GET: запрос на комментарии к событию по идентификатору {}", eventId);
        return commentService.getAllCommentsByEvent(eventId);
    }

    @GetMapping(value = "/{commentId}")
    public CommentDto getComById(@PathVariable Long eventId, @PathVariable Long commentId) {
        log.info("GET: запрос на комментарий {} к событию {}", commentId, eventId);
        return commentService.getComById(eventId, commentId);
    }
}
