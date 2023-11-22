package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.error.exception.ConflictException;
import ru.practicum.error.exception.EventNotFoundException;
import ru.practicum.error.exception.RequestNotFoundException;
import ru.practicum.error.exception.UserNotFoundException;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventStatus;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestStatus;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.model.Request;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public RequestDto createReq(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new EventNotFoundException(String.format("Event with id=%d was not found", eventId),
                        "The required object was not found."));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(String.format("User with id=%d was not found", userId),
                        "The required object was not found."));

        if (requestRepository.findFirstWhenRequesterIdIsAndEventIdIs(userId, eventId)) {
            throw new ConflictException((String.format("The request for user's " +
                    "participation in tne event id=%d already exists", eventId)), "Incorrect request parameters.");
        }
        if (event.getInitiator().getId().equals(user.getId())) {
            throw new ConflictException("User cannot send participation request in self-published event",
                    "Incorrect request parameters.");
        }
        if (!event.getState().equals(EventStatus.PUBLISHED)) {
            throw new ConflictException("Request can be made only for published events",
                    "Incorrect request parameters.");
        }
        if (event.getParticipantLimit() > 0 && event.getRequests().size() == event.getParticipantLimit()) {
            throw new ConflictException("Participants limit reached", "Incorrect request parameters.");
        }

        Request request = new Request();
        request.setRequester(user);
        if (!event.getRequestModeration()) {
            request.setStatus(RequestStatus.CONFIRMED);
        } else {
            request.setStatus(RequestStatus.PENDING);
        }
        request.setCreated(LocalDateTime.now());

        request = requestRepository.save(request);
        if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
            event.getRequests().add(request);
            eventRepository.save(event);
        }
        return requestMapper.toRequestDto(request);

    }

    @Transactional(readOnly = true)
    public List<RequestDto> getReqByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(String.format("User with id=%d was not found", userId),
                    "The required object was not found.");
        }
        return requestRepository.findAllByRequesterId(userId)
                .stream()
                .map(requestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    public RequestDto cancelReq(Long userId, Long requestId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(String.format("User with id=%d was not found", userId),
                    "The required object was not found.");
        }
        Request request = requestRepository.findByIdAndRequesterId(requestId, userId).orElseThrow(
                () -> new RequestNotFoundException(String.format("Request with id=%d was not found", requestId),
                        "The required object was not found."));
        if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
            Event event = request.getEvent();
            event.getRequests().remove(request);
            eventRepository.save(event);
        }

        request.setStatus(RequestStatus.CANCELED);
        return requestMapper.toRequestDto(requestRepository.save(request));
    }
}
