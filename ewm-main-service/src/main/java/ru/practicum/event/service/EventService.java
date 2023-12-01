package ru.practicum.event.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.UpdateEventDto;
import ru.practicum.event.enums.EventSort;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestStatusUpdateDto;
import ru.practicum.request.dto.RequestsResultStatusDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс сервиса событий
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
public interface EventService {
    /**
     * Метод получения списка полной информации о событиях из хранилища по заданным параметрам
     *
     * @param users      - список id пользователей, чьи события нужно найти
     * @param states     - список состояний, в которых находятся искомые события
     * @param categories - список id категорий, в которых будет вестись поиск
     * @param rangeStart - дата и время, не раньше которых должно произойти событие
     * @param rangeEnd   - дата и время, не позже которых должно произойти событие
     * @return список объектов EventFullDto {@link EventFullDto}
     */
    List<EventFullDto> getEventsByParametersForAdmin(List<Long> users, List<String> states,
                                                     List<Long> categories, LocalDateTime rangeStart,
                                                     LocalDateTime rangeEnd, Pageable pageable);

    /**
     * Метод редактирования данных события и его статуса (отклонение или публикация) в хранилище
     *
     * @param eventId        - идентификатор категории
     * @param updateEventDto {@link UpdateEventDto} - данные для обновления
     * @return копию объекта EventFullDto {@link EventFullDto} с обновленными полями
     */
    EventFullDto updateEventByAdmin(Long eventId, UpdateEventDto updateEventDto);

    /**
     * Метод добавления события в хранилище
     *
     * @param userId      - идентификатор создателя события
     * @param newEventDto {@link NewEventDto}
     * @return {@link EventFullDto} и код ответа API 201
     */
    EventFullDto createEvent(Long userId, NewEventDto newEventDto);

    /**
     * Метод получения списка всех событий, добавленных текущим пользователем, из хранилища
     *
     * @param userId - идентификатор пользователя, запрашивающего список
     * @return список объектов CategoryDto {@link CategoryDto}
     */
    List<EventShortDto> getEventsByUser(Long userId, Pageable pageable);

    /**
     * Метод получения полной информации о событии, добавленном текущим пользователем
     *
     * @param userId  - идентификатор создателя события
     * @param eventId - идентификатор события
     * @return {@link EventFullDto}
     */
    EventFullDto getEventByIdForInitiator(Long userId, Long eventId);

    /**
     * Метод обновления информации о событии, добавленном текущим пользователем в хранилище
     *
     * @param userId             - идентификатор создателя события
     * @param eventId            - идентификатор события
     * @param updateEventUserDto {@link UpdateEventDto} - данные для обновления
     * @return копию объекта {@link EventFullDto} с обновленными полями
     */
    EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventDto updateEventUserDto);

    /**
     * Метод получения информации о запросах на участие в событии текущего пользователя из хранилища
     *
     * @param userId  - идентификатор создателя
     * @param eventId - идентификатор события
     * @return список объектов RequestDto {@link RequestDto}
     */
    List<RequestDto> getRequestsByEvent(Long userId, Long eventId);

    /**
     * Метод изменения статуса заявок на участие (подтверждена, отклонена) в событии
     * текущего пользователя
     *
     * @param userId                 - идентификатор создателя
     * @param eventId                - идентификатор события
     * @param requestStatusUpdateDto {@link RequestStatusUpdateDto} - данные для обновления
     * @return список объектов RequestDto {@link RequestDto}
     */
    RequestsResultStatusDto updateStatusEventRequests(Long eventId, Long userId,
                                                      RequestStatusUpdateDto requestStatusUpdateDto);

    /**
     * Метод получения списка информации о событиях из хранилища по заданным параметрам
     *
     * @param text          - текст для поиска в содержимом аннотации и подробном описании события
     * @param paid          - поиск только платных/бесплатных событий
     * @param categories    - список идентификаторов категорий, в которых будет вестись поиск
     * @param rangeStart    - дата и время, не раньше которых должно произойти событие
     * @param rangeEnd      - дата и время, не позже которых должно произойти событие
     * @param onlyAvailable - только события, у которых не исчерпан лимит запросов на участие
     * @param sort          - Вариант сортировки: по дате события или по количеству просмотров
     * @return список EventShortDto {@link EventShortDto}, подходящих по условия поиска
     */
    List<EventShortDto> getEventsByParametersForUsers(String text, List<Long> categories,
                                                      Boolean paid, LocalDateTime rangeStart,
                                                      LocalDateTime rangeEnd, Boolean onlyAvailable,
                                                      EventSort sort, Pageable pageable,
                                                      HttpServletRequest request);

    /**
     * Метод получения полной информации об опубликованном событии по его идентификатору
     *
     * @param id - идентификатор события
     * @return {@link EventFullDto}
     */
    EventFullDto getEventById(Long id, HttpServletRequest request);
}
