package ru.practicum.request.mapper;

import org.mapstruct.Mapper;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.model.Request;

@Mapper
public interface RequestMapper {
    RequestDto toRequestDto(Request request);
}
