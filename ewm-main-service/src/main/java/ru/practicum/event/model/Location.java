package ru.practicum.event.model;

import lombok.*;

/**
 * Класс локации со свойствами <b>lat</b> и <b>lon</b>  для работы через REST-интерфейс
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Location {
    /**
     * Поле широта места
     */
    private Float lat;

    /**
     * Поле долгота места
     */
    private Float lon;
}
