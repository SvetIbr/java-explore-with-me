package ru.practicum.comment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.CommentShortDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.dto.UpdateCommentDto;
import ru.practicum.comment.model.Comment;

import static ru.practicum.constants.Constants.DATE_TIME_FORMAT;

/**
 * Mapper-класс для преобразования объектов сервиса комментариев
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface CommentMapper {
    /**
     * Метод преобразования объекта Comment в CommentDto
     *
     * @param comment {@link Comment}
     * @return {@link CommentDto}
     */
    @Mapping(source = "created", target = "created", dateFormat = DATE_TIME_FORMAT)
    CommentDto toCommentDto(Comment comment);

    /**
     * Метод преобразования объекта NewCommentDto в Comment
     *
     * @param newCommentDto {@link NewCommentDto}
     * @return {@link Comment}
     */
    Comment toComment(NewCommentDto newCommentDto);

    /**
     * Метод обновления объекта Comment из данных Comment и UpdateCommentDto
     *
     * @param updateCommentDto {@link UpdateCommentDto}
     * @param comment          {@link Comment}
     * @return {@link Comment}
     */
    Comment updateComment(UpdateCommentDto updateCommentDto, @MappingTarget Comment comment);

    /**
     * Метод преобразования объекта Comment в CommentShortDto
     *
     * @param comment {@link Comment}
     * @return {@link CommentShortDto}
     */
    @Mapping(source = "created", target = "created", dateFormat = DATE_TIME_FORMAT)
    CommentShortDto toCommentShortDto(Comment comment);
}
