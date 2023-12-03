package ru.practicum.compilation.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс подборки событий для добавления в хранилище со свойствами <b>pinned</b>,
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
public class NewCompilationDto {
    /**
     * Поле список событий, входящих в подборку
     */
    private Set<Long> events = new HashSet<>();

    /**
     * Поле закреплена ли подборка на главной странице сайта
     */
    private boolean pinned;

    /**
     * Поле заголовок
     */
    @NotNull
    @NotBlank
    @Length(min = 1, max = 50)
    private String title;
}
