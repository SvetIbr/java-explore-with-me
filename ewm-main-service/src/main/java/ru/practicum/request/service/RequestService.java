package ru.practicum.request.service;

import ru.practicum.event.model.Event;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestStatusUpdateDto;
import ru.practicum.request.dto.RequestsResultStatusDto;

import java.util.List;

/**
 * Интерфейс сервиса заявок на участие
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
public interface RequestService {
    /**
     * Метод добавления заявки на участие в хранилище
     *
     * @param userId  - идентификатор текущего пользователя
     * @param eventId - идентификатор события
     * @return {@link RequestDto} и код ответа API 201
     */
    RequestDto createReq(Long userId, Long eventId);

    /**
     * Метод получения списка всех заявок на участие текущего пользователя в чужих событиях
     *
     * @param userId - идентификатор пользователя, запрашивающего список
     * @return список объектов RequestDto {@link RequestDto}
     */
    List<RequestDto> getReqByUser(Long userId);

    /**
     * Метод отмены своей заявки на участие в хранилище
     *
     * @param userId    - идентификатор текущего пользователя
     * @param requestId - идентификатор заявки
     * @return {@link RequestDto}
     */
    RequestDto cancelReq(Long userId, Long requestId);

    /**
     * Метод изменения статуса заявок на участие (подтверждена, отклонена) в событии текущего пользователя
     *
     * @param event                  - событие
     * @param requestStatusUpdateDto {@link RequestStatusUpdateDto} - данные для обновления
     * @return список объектов RequestDto {@link RequestDto}
     */
    RequestsResultStatusDto updateRequestsStatusByEvent(RequestStatusUpdateDto
                                                                requestStatusUpdateDto, Event event);
}
