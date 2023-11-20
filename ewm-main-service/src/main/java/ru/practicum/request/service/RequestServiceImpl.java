package ru.practicum.request.service;

import org.springframework.stereotype.Service;
import ru.practicum.request.dto.RequestDto;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    public RequestDto createReq(Long userId, Long eventId) {

    }

    public List<RequestDto> getReqByUser(Long userId) {

    }

    public RequestDto cancelReq(Long userId, Long requestId) {

    }
}
