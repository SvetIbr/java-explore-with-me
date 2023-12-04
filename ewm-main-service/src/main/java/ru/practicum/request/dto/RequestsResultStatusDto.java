package ru.practicum.request.dto;

import lombok.*;

import java.util.List;

/**
 * Класс результат подтверждения/отклонения заявок на участие в событии
 * со свойствами <b>confirmedRequests</b> и <b>rejectedRequests</b> для работы через REST-интерфейс
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
public class RequestsResultStatusDto {
    /**
     * Поле принятые заявки на участие
     */
    private List<RequestDto> confirmedRequests;

    /**
     * Поле отклоненные заявки на участие
     */
    private List<RequestDto> rejectedRequests;
}
