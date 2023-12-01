package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.event.model.Location;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Класс события для добавления в хранилище со свойствами <b>title</b>, <b>annotation</b>,
 * <b>category</b>, <b>paid</b>, <b>eventDate</b>, <b>description</b>,
 * <b>participantLimit</b>,<b>location</b>, <b>requestModeration</b> для работы через REST-интерфейс
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
public class NewEventDto {
    /**
     * Поле краткое описание
     */
    @NotNull
    @Length(min = 20, max = 2000)
    private String annotation;

    /**
     * Поле категория
     */
    @NotNull
    private Long category;

    /**
     * Поле описание
     */
    @NotNull
    @Length(min = 20, max = 7000)
    private String description;

    /**
     * Поле дата и время на которые намечено событие
     */
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private LocalDateTime eventDate;

    /**
     * Поле широта и долгота места проведения события
     */
    @NotNull
    private Location location;

    /**
     * Поле нужно ли оплачивать участие
     */
    private Boolean paid = false;

    /**
     * Поле ограничение на количество участников (значение 0 - означает отсутствие ограничения)
     */
    private Integer participantLimit = 0;

    /**
     * Поле нужна ли пре-модерация заявок на участие
     */
    private Boolean requestModeration = true;

    /**
     * Поле заголовок
     */
    @NotNull
    @Length(min = 3, max = 120)
    private String title;
}
