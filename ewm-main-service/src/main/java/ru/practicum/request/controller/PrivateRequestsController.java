package ru.practicum.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.service.RequestService;

import java.util.List;

/**
 * Класс закрытого (доступна только авторизованным пользователям) контроллера
 * для работы с сервисом запросов на участие в событии
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateRequestsController {
    /**
     * Поле сервис для работы с хранилищем запросов на участие
     */
    private final RequestService requestService;

    /**
     * Метод получения списка всех заявок на участие текущего пользователя в чужих событиях
     * из хранилища сервиса через запрос
     *
     * @param userId - идентификатор пользователя, запрашивающего список
     * @return список объектов RequestDto {@link RequestDto}
     */
    @GetMapping
    public List<RequestDto> getRequestsByUser(@PathVariable Long userId) {
        log.info("GET: запрос на получение всех заявок на участие от пользователя " +
                "с идентификатором {}", userId);
        return requestService.getReqByUser(userId);
    }

    /**
     * Метод добавления заявки на участие в хранилище сервиса через запрос
     *
     * @param userId  - идентификатор текущего пользователя
     * @param eventId - идентификатор события
     * @return {@link RequestDto} и код ответа API 201
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto createRequest(@PathVariable Long userId, @RequestParam Long eventId) {
        log.info("POST: создание заявки на участие в событии по идентификатору {} " +
                "от пользователя с идентификатором {}", eventId, userId);
        return requestService.createReq(userId, eventId);
    }

    /**
     * Метод отмены своей заявки на участие в хранилище сервиса через запрос
     *
     * @param userId    - идентификатор текущего пользователя
     * @param requestId - идентификатор заявки
     * @return {@link RequestDto}
     */
    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable Long requestId, @PathVariable Long userId) {
        log.info("PATCH: отмена заявки по идентификатору {} " +
                "от пользователя с идентификатором {}", requestId, userId);
        return requestService.cancelReq(userId, requestId);
    }
}
