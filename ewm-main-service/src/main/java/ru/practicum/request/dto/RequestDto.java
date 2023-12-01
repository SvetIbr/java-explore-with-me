package ru.practicum.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Класс заявки на участие в событии со свойствами <b>id</b>, <b>event</b>, <b>created</b>,
 * <b>requester</b>, <b>status</b> для работы через REST-интерфейс
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
public class RequestDto {
    /**
     * Поле идентификатор
     */
    private Long id;

    /**
     * Поле событие
     */
    private Long event;

    /**
     * Поле дата и время создания заявки
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    /**
     * Поле идентификатор пользователя, отправившего заявку
     */
    private Long requester;

    /**
     * Поле статус заявки
     */
    private RequestStatus status;
}
