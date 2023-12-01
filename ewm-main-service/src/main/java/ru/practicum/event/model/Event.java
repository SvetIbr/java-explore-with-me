package ru.practicum.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.category.model.Category;
import ru.practicum.event.enums.EventState;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

import static ru.practicum.constants.Constants.DATE_TIME_FORMAT;

/**
 * Класс события со свойствами <b>id</b>, <b>title</b>, <b>annotation</b>,
 * <b>category</b>, <b>paid</b>, <b>eventDate</b>, <b>initiator</b>, <b>description</b>,
 * <b>participantLimit</b>, <b>state</b>, <b>createdOn</b>, <b>lon</b>, <b>lat</b>,
 * <b>requestModeration</b>, <b>confirmedRequests</b> и <b>publishedOn</b> для работы с базой данных
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Entity
@Table(name = "events")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Event {
    /**
     * Поле идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Поле краткое описание
     */
    private String annotation;

    /**
     * Поле категория
     */
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * Поле количество одобренных заявок на участие в данном событии
     */
    @Column(name = "confirmed_requests")
    private Integer confirmedRequests;

    /**
     * Поле дата и время на которые намечено событие
     */
    @Column(name = "event_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime eventDate;

    /**
     * Поле инициатор события
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "initiator_id")
    private User initiator;

    /**
     * Поле нужно ли оплачивать участие
     */
    @Column(columnDefinition = "boolean default false")
    private Boolean paid;

    /**
     * Поле заголовок
     */
    private String title;

    /**
     * Поле дата создания
     */
    @Column(name = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime createdOn;

    /**
     * Поле описание
     */
    private String description;

    /**
     * Поле долгота места проведения события
     */
    @Column(name = "lon")
    private double lon;

    /**
     * Поле широта места проведения события
     */
    @Column(name = "lat")
    private double lat;

    /**
     * Поле ограничение на количество участников (значение 0 - означает отсутствие ограничения)
     */
    @Column(name = "participant_limit", columnDefinition = "integer default 0")
    private Integer participantLimit;

    /**
     * Поле дата и время публикации события
     */
    @Column(name = "published")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime publishedOn;

    /**
     * Поле нужна ли пре-модерация заявок на участие
     */
    @Column(name = "request_moderation", columnDefinition = "boolean default true")
    private Boolean requestModeration;

    /**
     * Поле состояние
     */
    @Enumerated(EnumType.STRING)
    private EventState state;
}
