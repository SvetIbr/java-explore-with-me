package ru.practicum.request.dto;

import lombok.*;
import ru.practicum.event.enums.StatusForUpdate;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Класс изменение статусов запросов на участие в событии текущего пользователя
 * со свойствами <b>requestIds</b> и <b>status</b> для работы через REST-интерфейс
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
public class RequestStatusUpdateDto {
    /**
     * Поле идентификаторы запросов на участие в событии текущего пользователя
     */
    @NotNull
    private List<Long> requestIds;

    /**
     * Поле новый статус запроса на участие в событии текущего пользователя
     */
    @NotNull
    private StatusForUpdate status;
}
