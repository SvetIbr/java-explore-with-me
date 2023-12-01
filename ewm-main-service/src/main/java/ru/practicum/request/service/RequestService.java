package ru.practicum.request.service;

import ru.practicum.event.model.Event;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestStatusUpdateDto;
import ru.practicum.request.dto.RequestsResultStatusDto;

import java.util.List;

public interface RequestService {
    RequestDto createReq(Long userId, Long eventId);

    List<RequestDto> getReqByUser(Long userId);

    RequestDto cancelReq(Long userId, Long requestId);

    RequestsResultStatusDto updateRequestsStatusByEvent(RequestStatusUpdateDto
                                                                requestStatusUpdateDto, Event event);
}
