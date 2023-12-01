package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.user.dto.UserShortDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Класс события с краткой информацией со свойствами <b>id</b>, <b>title</b>, <b>annotation</b>,
 * <b>category</b>, <b>paid</b>, <b>eventDate</b>, <b>initiator</b>, <b>confirmedRequests</b> и <b>views</b>
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
public class EventShortDto {
    /**
     * Поле идентификатор
     */
    private Long id;

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
     * Поле количество одобренных заявок на участие в данном событии
     */
    private Integer confirmedRequests;

    /**
     * Поле дата и время на которые намечено событие
     */
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    /**
     * Поле инициатор события
     */
    @NotNull
    private UserShortDto initiator;

    /**
     * Поле нужно ли оплачивать участие
     */
    @NotNull
    private Boolean paid;

    /**
     * Поле заголовок
     */
    @NotNull
    private String title;

    /**
     * Поле количество просмотрев события
     */
    private Integer views;
}
