package ru.practicum.user.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Класс пользователя со свойствами <b>id</b>, <b>name</b> и <b>email</b> для работы через REST-интерфейс
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    /**
     * Поле идентификатор
     */
    private Long id;

    /**
     * Поле имя
     */
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 2, max = 250)
    private String name;

    /**
     * Поле электронная почта
     */
    @NotNull
    @Email
    @Length(min = 6, max = 254)
    private String email;
}
