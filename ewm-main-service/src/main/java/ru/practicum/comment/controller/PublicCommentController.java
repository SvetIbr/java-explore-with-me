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

/**
 * Класс публичного (доступна без регистрации любому пользователю сети) контроллера
 * для работы с сервисом комментариев
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "events/{eventId}/comments")
public class PublicCommentController {
    /**
     * Поле сервис для работы с хранилищем комментариев
     */
    private final CommentService commentService;

    /**
     * Метод получения списка всех комментариев, оставленных к конкретному событию,
     * из хранилища сервиса через запрос (только у опубликованных событий и
     * только не заблокированные комментарии)
     *
     * @param eventId - идентификатор события
     * @return список объектов CommentDto {@link CommentDto}
     */
    @GetMapping
    public List<CommentDto> getAllCommentsByEvent(@PathVariable Long eventId) {
        log.info("GET: запрос на комментарии к событию по идентификатору {}", eventId);
        return commentService.getAllCommentsByEvent(eventId);
    }

    /**
     * Метод получения комментария по идентификатору, оставленного к конкретному событию,
     * из хранилища сервиса через запрос (только у опубликованных событий и
     * только не заблокированные комментарии)
     *
     * @param eventId   - идентификатор события
     * @param commentId - идентификатор комментария
     * @return список объектов CommentDto {@link CommentDto}
     */
    @GetMapping(value = "/{commentId}")
    public CommentDto getComById(@PathVariable Long eventId, @PathVariable Long commentId) {
        log.info("GET: запрос на комментарий {} к событию {}", commentId, eventId);
        return commentService.getComById(eventId, commentId);
    }
}
