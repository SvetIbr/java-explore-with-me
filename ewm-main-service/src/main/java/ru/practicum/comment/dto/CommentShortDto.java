package ru.practicum.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

/**
 * Класс с краткой информацией о комментарии со свойствами <b>id</b>, <b>text</b>, <b>author</b>,
 * <b>created</b>, <b>isChanged</b> и <b>isBlocked</b> для работы через REST-интерфейс
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentShortDto {
    /**
     * Поле идентификатор
     */
    private Long id;

    /**
     * Поле текст
     */
    private String text;

    /**
     * Поле автор
     */
    private UserShortDto author;

    /**
     * Поле дата и время создания
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    /**
     * Поле флаг, что комментарий был редактирован
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isChanged;

    /**
     * Поле флаг, что комментарий был заблокирован
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isBlocked;
}
