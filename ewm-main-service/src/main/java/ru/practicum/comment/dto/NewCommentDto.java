package ru.practicum.comment.dto;

import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Класс комментария для добавления в хранилище со свойством <b>text</b> для работы через REST-интерфейс
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
public class NewCommentDto {
    /**
     * Поле текст
     */
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 5, max = 7000)
    private String text;
}
