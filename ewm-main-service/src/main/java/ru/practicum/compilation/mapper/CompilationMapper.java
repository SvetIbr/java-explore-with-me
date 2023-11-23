package ru.practicum.compilation.mapper;

import org.mapstruct.Mapper;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.model.Compilation;

@Mapper
public interface CompilationMapper {
    Compilation toCompilation(NewCompilationDto newCompilationDto);
    CompilationDto toCompilationDto(Compilation compilation);
}
