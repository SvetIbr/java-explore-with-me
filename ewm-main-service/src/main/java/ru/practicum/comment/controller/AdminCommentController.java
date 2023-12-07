package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.BlockCommentDto;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс административного (для администраторов сервиса) контроллера для работы с сервисом комментариев
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "admin/comments")
public class AdminCommentController {
    /**
     * Поле сервис для работы с хранилищем комментариев
     */
    private final CommentService commentService;

    /**
     * Метод получения через запрос списка комментариев из хранилища сервиса
     * по заданным параметрам
     *
     * @param text              - текст для поиска в тексте комментария
     * @param users             - список id пользователей, чьи комментарии нужно найти
     * @param events            - список id событий, в комментариях которых будет вестись поиск
     * @param onlyUnlocked      - только не заблокированные комментарии
     * @param onlyBlocked       - только заблокированные комментарии
     * @param rangeCreatedStart - дата и время, не раньше которых был создан комментарий
     * @param rangeCreatedEnd   - дата и время, не позже которых был создан комментарий
     * @param from              - индекс первого элемента, начиная с 0
     * @param size              - количество элементов для отображения
     * @return список CommentDto {@link CommentDto}
     */
    @GetMapping
    public List<CommentDto> searchCommentsForAdmin(@RequestParam(name = "text", required = false) String text,
                                                   @RequestParam(name = "users", required = false)
                                                   List<Long> users,
                                                   @RequestParam(name = "events", required = false)
                                                   List<Long> events,
                                                   @RequestParam(name = "onlyUnlocked", required = false)
                                                   boolean onlyUnlocked,
                                                   @RequestParam(name = "onlyBlocked", required = false)
                                                   boolean onlyBlocked,
                                                   @RequestParam(name = "rangeCreatedStart", required = false)
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                   LocalDateTime rangeCreatedStart,
                                                   @RequestParam(name = "rangeCreatedEnd", required = false)
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                   LocalDateTime rangeCreatedEnd,
                                                   @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                   @RequestParam(name = "size", defaultValue = "10") Integer size) {

        log.info("GET: получение комментариев с параметрами 'text': {}, 'users': {}, 'events': {}, " +
                        "'onlyUnlocked': {}, 'onlyBlocked': {}, 'rangeCreatedStart': {}, 'rangeCreatedEnd': {}, " +
                        "'from': {}, 'size': {}",
                text, users, events, onlyUnlocked, onlyBlocked, rangeCreatedStart, rangeCreatedEnd,
                from, size);
        return commentService.searchCommentsForAdmin(text, users, events, onlyUnlocked, onlyBlocked,
                rangeCreatedStart, rangeCreatedEnd, PageRequest.of(from / size, size));
    }


    /**
     * Метод блокирования для общего доступа комментария через запрос
     *
     * @param blockCommentDto - {@link BlockCommentDto}
     * @return список заблокированных CommentDto {@link CommentDto}
     */
    @PatchMapping
    public List<CommentDto> blockComments(@RequestBody BlockCommentDto blockCommentDto) {
        log.info("PATCH: блокировка комментариев админом с идентификаторами: {}",
                blockCommentDto.getCommentIdsForBlock());
        return commentService.blockComments(blockCommentDto.getCommentIdsForBlock());
    }
}
