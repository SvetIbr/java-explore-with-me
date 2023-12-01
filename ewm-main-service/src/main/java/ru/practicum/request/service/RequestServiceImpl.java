package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.error.exception.*;
import ru.practicum.event.enums.StatusForUpdate;
import ru.practicum.event.model.Event;
import ru.practicum.event.enums.EventState;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.request.dto.RequestStatus;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestStatusUpdateDto;
import ru.practicum.request.dto.RequestsResultStatusDto;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.model.Request;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        Event event = checkAndReturnEventInStorage(eventId);

        User user = checkAndReturnUserInStorage(userId);

        checkRequestBeforeCreate(user, event);

        Request request = new Request();
        request.setRequester(user);
        request.setCreated(LocalDateTime.now());

        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            request.setStatus(RequestStatus.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            event = eventRepository.save(event);
        } else {
            request.setStatus(RequestStatus.PENDING);
        }

        request.setEvent(event);
        request = requestRepository.save(request);
        return requestMapper.toRequestDto(request);
    }

    @Transactional(readOnly = true)
    public List<RequestDto> getReqByUser(Long userId) {
        checkUserInStorage(userId);

        return requestRepository.findAllByRequesterId(userId)
                .stream()
                .map(requestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    public RequestDto cancelReq(Long userId, Long requestId) {
        checkUserInStorage(userId);

        Request request = requestRepository.findByIdAndRequesterId(requestId, userId).orElseThrow(
                () -> new NotFoundException(String.format("Request with id=%d was not found", requestId)));

        if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
            Event event = request.getEvent();
            event.setConfirmedRequests(event.getConfirmedRequests() - 1);
            eventRepository.save(event);
        }

        request.setStatus(RequestStatus.CANCELED);
        return requestMapper.toRequestDto(requestRepository.save(request));
    }

    public RequestsResultStatusDto updateRequestsStatusByEvent(RequestStatusUpdateDto
                                                                       requestStatusUpdateDto, Event event) {

        List<Request> requests = requestRepository
                .findAllByIdInAndEventId(requestStatusUpdateDto.getRequestIds(), event.getId());

        if (requests.size() != requestStatusUpdateDto.getRequestIds().size()) {
            throw new NotFoundException("Incorrect request id(s) received in the request body.");
        }

        for (Request request : requests) {
            if (!request.getStatus().equals(RequestStatus.PENDING)) {
                throw new ConflictException("Only requests with status " +
                        "'Pending' can be accepted or rejected.");
            }
        }

        List<RequestDto> confirmedRequests = new ArrayList<>();
        List<RequestDto> rejectedRequests = new ArrayList<>();


        if (requestStatusUpdateDto.getStatus() == StatusForUpdate.REJECTED) {
            requests.forEach(request -> {
                request.setStatus(RequestStatus.REJECTED);
                requestRepository.save(request);
                rejectedRequests.add(requestMapper.toRequestDto(request));
            });
            return new RequestsResultStatusDto(confirmedRequests, rejectedRequests);
        }

        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            return new RequestsResultStatusDto(
                    requests.stream().map(requestMapper::toRequestDto).collect(Collectors.toList()),
                    new ArrayList<>()
            );
        }

        if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ConflictException("Failed to accept request. " +
                    "Reached max participant limit for event id = " + event.getId() + ".");
        }

        requests.forEach(request -> {
            if (event.getConfirmedRequests() < event.getParticipantLimit()) {
                request.setStatus(RequestStatus.CONFIRMED);
                requestRepository.save(request);
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                confirmedRequests.add(requestMapper.toRequestDto(request));
            } else {
                request.setStatus(RequestStatus.REJECTED);
                requestRepository.save(request);
                rejectedRequests.add(requestMapper.toRequestDto(request));
            }
        });

        if (!confirmedRequests.isEmpty()) {
            eventRepository.save(event);
        }

        return new RequestsResultStatusDto(confirmedRequests, rejectedRequests);
    }

    private Event checkAndReturnEventInStorage(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException(String.format("Event with id=%d was not found", eventId)));
    }

    private User checkAndReturnUserInStorage(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(String.format("User with id=%d was not found", userId)));
    }

    private void checkRequestBeforeCreate(User user, Event event) {
        if (requestRepository.existsByRequesterIdAndEventId(user.getId(), event.getId())) {
            throw new ConflictException((String.format("The request for user's " +
                    "participation in tne event id=%d already exists", event.getId())));
        }
        if (event.getInitiator().getId().equals(user.getId())) {
            throw new ConflictException("User cannot send participation request in self-published event");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Request can be made only for published events");
        }
        if (event.getParticipantLimit() > 0 &&
                (event.getConfirmedRequests() >= event.getParticipantLimit())) {
            throw new ConflictException("Participants limit reached");
        }
    }

    private void checkUserInStorage(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("User with id=%d was not found", userId));
        }
    }

}
