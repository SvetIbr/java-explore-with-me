package ru.practicum.category.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Класс категории для добавления в хранилище
 * со свойствами <b>id</b> и <b>name</b> для работы через REST-интерфейс
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
public class NewCategoryDto {
    /**
     * Поле наименование
     */
    @NotNull
    @Length(min = 1, max = 50)
    @NotEmpty
    @NotBlank
    private String name;
}
