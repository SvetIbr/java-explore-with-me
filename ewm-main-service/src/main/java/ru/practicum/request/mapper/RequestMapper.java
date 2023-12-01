package ru.practicum.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.model.Request;

/**
 * Mapper-класс для преобразования объектов сервиса заявок на участие в событии
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface RequestMapper {
    /**
     * Метод преобразования объекта Request в RequestDto
     *
     * @param request {@link Request}
     * @return {@link RequestDto}
     */
    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    RequestDto toRequestDto(Request request);
}
