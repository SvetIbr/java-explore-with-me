package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.HitDto;
import ru.practicum.model.Hit;

/**
 * Интерфейс Mapper-класса для преобразования объектов сервиса статистики
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface StatsMapper {
    /**
     * Метод преобразования объекта HitDto в Hit
     *
     * @param hitDto {@link HitDto}
     * @return {@link Hit}
     */
    Hit toHit(HitDto hitDto);
}
