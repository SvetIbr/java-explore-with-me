package ru.practicum.event.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.UpdateEventDto;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestStatusUpdateDto;
import ru.practicum.request.dto.RequestsResultStatusDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    public List<EventFullDto> getEventsByParametersForAdmin(List<Long> users,
                                                            List<String> states,
                                                            List<Long> categories,
                                                            String rangeStart,
                                                            String rangeEnd,
                                                            PageRequest of) {

    }

    public EventFullDto updateEventByAdmin(Long eventId, UpdateEventDto updateEventDto) {

    }

    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {

    }

    public List<EventShortDto> getEventsByUser(Long userId, PageRequest of) {

    }

    public EventFullDto getEventByIdForInitiator(Long userId, Long eventId) {

    }

    public EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventDto updateEventUserDto) {

    }

    public List<RequestDto> getRequestsByEvent(Long userId, Long eventId) {

    }

    public RequestsResultStatusDto updateStatusEventRequests(Long eventId,
                                                             Long userId,
                                                             RequestStatusUpdateDto requestStatusUpdateDto) {

    }

    public List<EventShortDto> getEventsByParametersForUsers(String text,
                                                             List<Long> categories,
                                                             Boolean paid,
                                                             String rangeStart,
                                                             String rangeEnd,
                                                             Boolean onlyAvailable,
                                                             String sort,
                                                             PageRequest of,
                                                             HttpServletRequest request) {

    }

    public EventFullDto getEventById(Long id, HttpServletRequest request) {

    }
}
