package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "admin/comments")
public class AdminCommentController {
    private final CommentService commentService;

    // поиск комментариев по критериям
    @GetMapping
    public List<CommentDto> searchCommentsForAdmin(@RequestParam(name = "text", required = false) String text,
                                                   @RequestParam(name = "users", required = false)
                                                   List<Long> users,
                                                   @RequestParam(name = "events", required = false)
                                                       List<Long> events,
                                                   @RequestParam(required = false) boolean onlyUnlocked,
                                                   @RequestParam(required = false) boolean onlyBlocked,
                                                   @RequestParam(name = "rangeCreatedStart", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                       LocalDateTime rangeCreatedStart,
                                             @RequestParam(name = "rangeCreatedEnd", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                       LocalDateTime rangeCreatedEnd,
                                             @RequestParam(name = "from", defaultValue = "0") Integer from,
                                             @RequestParam(name = "size", defaultValue = "10") Integer size) {

        log.info("GET: получение комментариев с параметрами 'text': {}, 'users': {}, 'statuses': {}, " +
                "'events': {}, 'rangeStart': {}, 'rangeEnd': {}, 'from': {}, 'size': {}",
                text, users, events, onlyUnlocked, rangeCreatedStart, rangeCreatedEnd, from, size);
        return commentService.searchCommentsForAdmin(text, users, events, onlyUnlocked, onlyBlocked,
                rangeCreatedStart, rangeCreatedEnd, PageRequest.of(from / size, size));
    }


    //заблокировать комментарий - не будут отображаться
    @PatchMapping
    public List<CommentDto> blockComments(@RequestBody List<Long> ids) {
        log.info("PATCH: блокировка комментариев админом с идентификаторами: {}", ids);
        return commentService.blockComments(ids);
    }
}
