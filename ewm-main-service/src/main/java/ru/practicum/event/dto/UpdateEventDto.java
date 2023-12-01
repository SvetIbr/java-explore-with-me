package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.event.enums.StateAction;
import ru.practicum.event.model.Location;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Future;
import java.time.LocalDateTime;

/**
 * Класс события для обновления данных со свойствами <b>title</b>, <b>annotation</b>,
 * <b>category</b>, <b>paid</b>, <b>eventDate</b>, <b>description</b>,
 * <b>participantLimit</b>,<b>location</b>, <b>requestModeration</b> и <b>stateAction</b>
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
public class UpdateEventDto {
    /**
     * Поле краткое описание
     */
    @Length(min = 20, max = 2000)
    private String annotation;

    /**
     * Поле категория
     */
    private Long category;

    /**
     * Поле описание
     */
    @Length(min = 20, max = 7000)
    private String description;

    /**
     * Поле дата и время на которые намечено событие
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private LocalDateTime eventDate;

    /**
     * Поле широта и долгота места проведения события
     */
    private Location location;

    /**
     * Поле нужно ли оплачивать участие
     */
    @Column(columnDefinition = "boolean default false")
    private Boolean paid;

    /**
     * Поле ограничение на количество участников (значение 0 - означает отсутствие ограничения)
     */
    @Column(columnDefinition = "integer default 0")
    private Integer participantLimit;

    /**
     * Поле нужна ли пре-модерация заявок на участие
     */
    @Column(columnDefinition = "boolean default true")
    private Boolean requestModeration;

    /**
     * Поле заголовок
     */
    @Length(min = 3, max = 120)
    private String title;

    /**
     * Поле изменение состояния события
     */
    @Enumerated(EnumType.STRING)
    private StateAction stateAction;
}
