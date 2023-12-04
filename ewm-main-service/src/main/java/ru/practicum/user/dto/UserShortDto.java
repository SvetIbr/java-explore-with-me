package ru.practicum.user.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Класс пользователя (краткая информация) со свойствами <b>id</b> и <b>name</b>
 * для работы через REST-интерфейс
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
public class UserShortDto {
    /**
     * Поле идентификатор
     */
    @NotNull
    private Long id;

    /**
     * Поле имя
     */
    @NotNull
    private String name;
}
