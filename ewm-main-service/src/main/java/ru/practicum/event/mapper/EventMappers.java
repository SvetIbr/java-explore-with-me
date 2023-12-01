package ru.practicum.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.service.CategoryService;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.UpdateEventDto;
import ru.practicum.event.model.Event;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.mapper.UserMapper;

import static ru.practicum.constants.Constants.DATE_TIME_FORMAT;

/**
 * Mapper-класс для преобразования объектов сервиса событий
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CategoryService.class,
        CategoryMapper.class, RequestRepository.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventMappers {
    /**
     * Метод преобразования объекта Event в EventFullDto
     *
     * @param event {@link Event}
     * @return {@link EventFullDto}
     */
    @Mapping(target = "location.lon", source = "lon")
    @Mapping(target = "location.lat", source = "lat")
    @Mapping(target = "views", constant = "0")
    EventFullDto toEventFullDto(Event event);

    /**
     * Метод преобразования объекта Event в EventShortDto
     *
     * @param event {@link Event}
     * @return {@link EventShortDto}
     */
    EventShortDto toEventShortDto(Event event);

    /**
     * Метод преобразования объекта NewEventDto в Event
     *
     * @param newEventDto {@link NewEventDto}
     * @return {@link Event}
     */
    @Mapping(target = "lon", source = "location.lon")
    @Mapping(target = "lat", source = "location.lat")
    @Mapping(target = "state", constant = "PENDING")
    @Mapping(target = "confirmedRequests", constant = "0")
    @Mapping(source = "eventDate", target = "eventDate", dateFormat = DATE_TIME_FORMAT)
    Event toEvent(NewEventDto newEventDto);

    /**
     * Метод обновления информации объекта Event из UpdateEventDto и Event
     *
     * @param updateEventDto {@link UpdateEventDto}
     * @param event          {@link Event}
     * @return {@link Event}
     */
    @Mapping(source = "eventDate", target = "eventDate", dateFormat = DATE_TIME_FORMAT)
    Event updateEvent(UpdateEventDto updateEventDto, @MappingTarget Event event);
}
