package ru.practicum.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.dto.RequestsResultStatusDto;
import ru.practicum.request.model.Request;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestMapper {
   @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    //@Mapping(target = "created", source = "createdOn")
    RequestDto toRequestDto(Request request);
}
