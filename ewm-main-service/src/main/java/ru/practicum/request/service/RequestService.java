package ru.practicum.request.service;

import ru.practicum.request.dto.RequestDto;

import java.util.List;

public interface RequestService {
    RequestDto createReq(Long userId, Long eventId);

    List<RequestDto> getReqByUser(Long userId);

    RequestDto cancelReq(Long userId, Long requestId);
}
