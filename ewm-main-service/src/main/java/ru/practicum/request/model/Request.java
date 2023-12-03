package ru.practicum.request.model;

import lombok.*;
import ru.practicum.event.model.Event;
import ru.practicum.request.dto.RequestStatus;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс заявки на участие в событии со свойствами <b>id</b>, <b>event</b>, <b>created</b>,
 * <b>requester</b>, <b>status</b> для работы с базой данных
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Entity
@Table(name = "requests")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Request {
    /**
     * Поле идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Поле событие
     */
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    /**
     * Поле дата и время создания заявки
     */
    private LocalDateTime created;

    /**
     * Поле пользователь, отправивший заявку
     */
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    /**
     * Поле статус заявки
     */
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
