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

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "created", target = "created", dateFormat = DATE_TIME_FORMAT)
    CommentDto toCommentDto(Comment comment);

    Comment toComment(NewCommentDto newCommentDto);

    Comment updateComment(UpdateCommentDto updateCommentDto, @MappingTarget Comment comment);

    @Mapping(source = "created", target = "created", dateFormat = DATE_TIME_FORMAT)
    CommentShortDto toCommentShortDto(Comment comment);
}
