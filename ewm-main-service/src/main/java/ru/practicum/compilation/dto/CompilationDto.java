package ru.practicum.compilation.dto;

import lombok.*;
import ru.practicum.event.dto.EventShortDto;

import java.util.List;

/**
 * Класс подборки событий со свойствами <b>id</b>, <b>pinned</b>, <b>title</b> и <b>events</b>
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
public class CompilationDto {
    /**
     * Поле идентификатор
     */
    private Long id;

    /**
     * Поле список событий, входящих в подборку
     */
    private List<EventShortDto> events;

    /**
     * Поле закреплена ли подборка на главной странице сайта
     */
    private Boolean pinned;

    /**
     * Поле заголовок
     */
    private String title;
}
