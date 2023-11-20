package ru.practicum.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.service.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateRequestsController {
    private final RequestService requestService;

    @GetMapping
    public List<RequestDto> getRequestsByUser(@PathVariable Long userId) {
        log.info("GET: запрос на получение всех заявок на участие от пользователя " +
                "с идентификатором {}", userId);
        return requestService.getReqByUser(userId);
    }

    @PostMapping
    public RequestDto createRequest(@PathVariable Long userId, @RequestParam Long eventId) {
        log.info("POST: создание заявки на участие в событии по идентификатору {} " +
                "от пользователя с идентификатором {}", eventId, userId);
        return requestService.createReq(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable Long requestId, @PathVariable Long userId) {
        log.info("PATCH: отмена заявки по идентификатору {} " +
                "от пользователя с идентификатором {}", requestId, userId);
        return requestService.cancelReq(userId, requestId);
    }
}
