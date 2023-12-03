package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.event.enums.EventState;
import ru.practicum.event.model.Location;
import ru.practicum.user.dto.UserShortDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.constants.Constants.DATE_TIME_FORMAT;

/**
 * Класс события с полной информацией со свойствами <b>id</b>, <b>title</b>, <b>annotation</b>,
 * <b>category</b>, <b>paid</b>, <b>eventDate</b>, <b>initiator</b>, <b>description</b>,
 * <b>participantLimit</b>, <b>state</b>, <b>createdOn</b>, <b>location</b>, <b>requestModeration</b>,
 * <b>confirmedRequests</b>, <b>views</b> и <b>publishedOn</b> для работы через REST-интерфейс
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
public class EventFullDto {
    /**
     * Поле идентификатор
     */
    private Long id;

    /**
     * Поле заголовок
     */
    @NotNull
    private String title;

    /**
     * Поле краткое описание
     */
    @NotNull
    private String annotation;

    /**
     * Поле категория
     */
    @NotNull
    private CategoryDto category;

    /**
     * Поле нужно ли оплачивать участие
     */
    @NotNull
    private Boolean paid;

    /**
     * Поле дата и время на которые намечено событие
     */
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime eventDate;

    /**
     * Поле инициатор события
     */
    @NotNull
    private UserShortDto initiator;

    /**
     * Поле описание
     */
    private String description;

    /**
     * Поле ограничение на количество участников (значение 0 - означает отсутствие ограничения)
     */
    private Integer participantLimit;

    /**
     * Поле состояние
     */
    private EventState state;

    /**
     * Поле дата создания
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime createdOn;

    /**
     * Поле широта и долгота места проведения события
     */
    @NotNull
    private Location location;

    /**
     * Поле нужна ли пре-модерация заявок на участие
     */
    private Boolean requestModeration;

    /**
     * Поле количество одобренных заявок на участие в данном событии
     */
    private Integer confirmedRequests;

    /**
     * Поле количество просмотрев события
     */
    private Integer views;

    /**
     * Поле дата и время публикации события
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime publishedOn;
}
