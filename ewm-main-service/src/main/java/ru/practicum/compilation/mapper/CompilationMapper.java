package ru.practicum.compilation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationDto;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.event.mapper.EventMappers;
import ru.practicum.event.repository.EventRepository;

/**
 * Mapper-класс для преобразования объектов сервиса подборок событий
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Mapper(componentModel = "spring", uses = {EventMappers.class, EventRepository.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface CompilationMapper {
    /**
     * Метод преобразования объекта NewCompilationDto в Category
     *
     * @param newCompilationDto {@link NewCompilationDto}
     * @return {@link Compilation}
     */
    Compilation toCompilation(NewCompilationDto newCompilationDto);

    /**
     * Метод преобразования объекта Compilation в CompilationDto
     *
     * @param compilation {@link Compilation}
     * @return {@link CompilationDto}
     */
    CompilationDto toCompilationDto(Compilation compilation);

    /**
     * Метод обновления информации объекта Compilation из UpdateCompilationDto и Compilation
     *
     * @param updateCompilationDto {@link UpdateCompilationDto}
     * @param compilation          {@link Compilation}
     * @return {@link Compilation}
     */
    Compilation update(UpdateCompilationDto updateCompilationDto, @MappingTarget Compilation compilation);
}
