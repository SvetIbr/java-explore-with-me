package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.EndpointHit;
import ru.practicum.model.Hit;

@Mapper(componentModel = "spring")
public interface StatsMapper {
    @Mapping(target = "timestamp", source = "timestamp", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Hit toHit(EndpointHit endpointHit);
}
