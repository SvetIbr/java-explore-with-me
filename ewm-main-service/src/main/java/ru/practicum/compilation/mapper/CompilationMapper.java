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

@Mapper(componentModel = "spring",uses = {EventMappers.class, EventRepository.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface CompilationMapper {
    Compilation toCompilation(NewCompilationDto newCompilationDto);
    CompilationDto toCompilationDto(Compilation compilation);
    Compilation update(UpdateCompilationDto updateCompilationDto, @MappingTarget Compilation compilation);

}
