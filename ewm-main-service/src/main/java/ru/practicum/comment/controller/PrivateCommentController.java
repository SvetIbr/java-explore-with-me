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

/**
 * Класс закрытого (доступна только авторизованным пользователям) контроллера
 * для работы с сервисом комментариев
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}")
public class PrivateCommentController {
    /**
     * Поле сервис для работы с хранилищем комментариев
     */
    private final CommentService commentService;

    /**
     * Метод получения списка всех комментариев, добавленных текущим пользователем,
     * из хранилища сервиса через запрос
     *
     * @param userId - идентификатор пользователя, запрашивающего список
     * @return список объектов CommentDto {@link CommentDto}
     */
    @GetMapping(value = "/comments")
    public List<CommentDto> getAllCommentsForAuthor(@PathVariable Long userId) {
        log.info("GET: запрос на все оставленные комментарии от пользователя " +
                "идентификатору {}", userId);
        return commentService.getAllCommentsForAuthor(userId);
    }

    /**
     * Метод удаления комментария, добавленного текущим пользователем, по идентификатору
     * из хранилища сервиса через запрос
     *
     * @param userId    - идентификатор автора
     * @param commentId - идентификатор комментария
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/comments/{commentId}")
    public void deleteComById(@PathVariable Long userId, @PathVariable Long commentId) {
        log.info("DELETE: удаление комментария по идентификатору {} от пользователя {}", commentId, userId);
        commentService.deleteComById(userId, commentId);
    }

    /**
     * Метод добавления комментария в хранилище сервиса через запрос
     *
     * @param userId        - идентификатор автора
     * @param eventId       - идентификатор события, к которому оставляют комментарий
     * @param newCommentDto - информация о новом комментарии {@link NewCommentDto}
     * @return копию объекта CommentDto {@link CommentDto} с добавленными из хранилища данными и статус 201
     */
    @PostMapping(value = "/events/{eventId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@PathVariable Long userId, @PathVariable Long eventId,
                                 @Valid @RequestBody NewCommentDto newCommentDto) {
        log.info("POST: добавление комментария к событию {} пользователем {} с параметрами {}",
                eventId, userId, newCommentDto);
        return commentService.addComment(userId, eventId, newCommentDto);
    }

    /**
     * Метод получения комментариев текущего пользователя, оставленных к конкретному событию,
     * из хранилища сервиса через запрос
     *
     * @param userId  - идентификатор автора
     * @param eventId - идентификатор события
     * @return список объектов CommentDto {@link CommentDto}
     */
    @GetMapping(value = "/events/{eventId}/comments")
    public List<CommentDto> getAllUserCommentsByEventId(@PathVariable Long eventId, @PathVariable Long userId) {
        log.info("GET: запрос на все комментарии пользователя {}, оставленные к событию {}", userId, eventId);
        return commentService.getAllCommentsForAuthorByEventId(userId, eventId);
    }

    /**
     * Метод обновления информации конкретного комментария текущего пользователя,
     * оставленного к конкретному событию, в хранилище сервиса через запрос
     *
     * @param userId           - идентификатор автора
     * @param eventId          - идентификатор события
     * @param commentId        - идентификатор комментария
     * @param updateCommentDto - данные для обновления
     * @return объект CommentDto {@link CommentDto} с обновленными полями
     */
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
