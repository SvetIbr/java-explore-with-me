package ru.practicum.comment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Класс комментария со свойствами <b>id</b>, <b>text</b>, <b>author</b>,
 * <b>event</b>, <b>created</b>, <b>isChanged</b> и <b>isBlocked</b>
 * для работы с базой данных
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Entity
@Table(name = "comments")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    /**
     * Поле идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Поле текст
     */
    @NotBlank
    @Size(min = 5, max = 7000)
    private String text;

    /**
     * Поле автор
     */
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    /**
     * Поле краткая информация о событии, к которому оставлен комментарий
     */
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    /**
     * Поле дата и время создания
     */
    @Column(name = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    /**
     * Поле флаг, что комментарий был редактирован
     */
    @Column(name = "changed")
    private Boolean isChanged;

    /**
     * Поле флаг, что комментарий был заблокирован
     */
    @Column(name = "blocked")
    private Boolean isBlocked;
}
