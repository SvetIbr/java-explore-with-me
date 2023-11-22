package ru.practicum.compilation.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {
    List<CompilationDto> getCompilations(Boolean pinned, PageRequest of);

    CompilationDto getCompById(Long compId);

    CompilationDto createComp(NewCompilationDto newCompilationDto);

    void deleteCompById(Long compId);

    CompilationDto updateComp(NewCompilationDto newCompilationDto, Long compId);
}
