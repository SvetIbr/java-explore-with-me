package ru.practicum.compilation.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс подборки событий для обновления данных в хранилище со свойствами <b>pinned</b>,
 * <b>title</b> и <b>events</b> для работы через REST-интерфейс
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
public class UpdateCompilationDto {
    /**
     * Поле список событий, входящих в подборку
     */
    private Set<Long> events = new HashSet<>();

    /**
     * Поле закреплена ли подборка на главной странице сайта
     */
    private Boolean pinned;

    /**
     * Поле заголовок
     */
    @Length(min = 1, max = 50)
    private String title;
}
