package ru.practicum.comment.dto;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * Класс комментария для обновления данных со свойством <b>text</b>
 * для работы через REST-интерфейс
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
public class UpdateCommentDto {
    /**
     * Поле текст
     */
    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min = 5, max = 7000)
    private String text;
}
