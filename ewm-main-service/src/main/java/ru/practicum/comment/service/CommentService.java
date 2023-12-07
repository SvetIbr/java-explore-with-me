package ru.practicum.comment.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.dto.UpdateCommentDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс сервиса комментариев
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
public interface CommentService {
    /**
     * Метод получения списка всех комментариев, оставленных к конкретному событию,
     * из хранилища (только у опубликованных событий и
     * только не заблокированные комментарии)
     *
     * @param eventId - идентификатор события
     * @return список объектов CommentDto {@link CommentDto}
     */
    List<CommentDto> getAllCommentsByEvent(Long eventId);

    /**
     * Метод получения списка комментариев из хранилища по заданным параметрам
     *
     * @param text         - текст для поиска в тексте комментария
     * @param users        - список id пользователей, чьи комментарии нужно найти
     * @param events       - список id событий, в комментариях которых будет вестись поиск
     * @param onlyUnlocked - только не заблокированные комментарии
     * @param onlyBlocked  - только заблокированные комментарии
     * @param rangeStart   - дата и время, не раньше которых был создан комментарий
     * @param rangeEnd     - дата и время, не позже которых был создан комментарий
     * @return список CommentDto {@link CommentDto}
     */
    List<CommentDto> searchCommentsForAdmin(String text, List<Long> users, List<Long> events,
                                            boolean onlyUnlocked, boolean onlyBlocked,
                                            LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                            Pageable pageable);

    /**
     * Метод блокирования для общего доступа комментария
     *
     * @param ids - список идентификаторов комментариев, которые нужно заблокировать
     * @return список заблокированных CommentDto {@link CommentDto}
     */
    List<CommentDto> blockComments(List<Long> ids);

    /**
     * Метод добавления комментария в хранилище
     *
     * @param userId        - идентификатор автора
     * @param eventId       - идентификатор события, к которому оставляют комментарий
     * @param newCommentDto - информация о новом комментарии {@link NewCommentDto}
     * @return копию объекта CommentDto {@link CommentDto}
     */
    CommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto);

    /**
     * Метод обновления информации конкретного комментария текущего пользователя,
     * оставленного к конкретному событию в хранилище
     *
     * @param userId           - идентификатор автора
     * @param eventId          - идентификатор события
     * @param commentId        - идентификатор комментария
     * @param updateCommentDto - данные для обновления
     * @return объект CommentDto {@link CommentDto} с обновленными полями
     */
    CommentDto updateComment(Long commentId, Long userId, Long eventId, UpdateCommentDto updateCommentDto);

    /**
     * Метод получения комментариев автора, оставленных к конкретному событию,
     * из хранилища
     *
     * @param userId  - идентификатор автора
     * @param eventId - идентификатор события
     * @return список объектов CommentDto {@link CommentDto}
     */
    List<CommentDto> getAllCommentsForAuthorByEventId(Long userId, Long eventId);

    /**
     * Метод получения списка всех комментариев, добавленных текущим пользователем, из хранилища
     *
     * @param userId - идентификатор пользователя, запрашивающего список
     * @return список объектов CommentDto {@link CommentDto}
     */
    List<CommentDto> getAllCommentsForAuthor(Long userId);

    /**
     * Метод удаления комментария, добавленного текущим пользователем, по идентификатору
     *
     * @param userId    - идентификатор автора
     * @param commentId - идентификатор комментария
     */
    void deleteComById(Long userId, Long commentId);

    /**
     * Метод получения комментария по идентификатору, оставленного к конкретному событию,
     * из хранилища (только у опубликованных событий и
     * только не заблокированные комментарии)
     *
     * @param eventId   - идентификатор события
     * @param commentId - идентификатор комментария
     * @return список объектов CommentDto {@link CommentDto}
     */
    CommentDto getComById(Long eventId, Long commentId);
}
