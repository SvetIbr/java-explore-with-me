package ru.practicum.compilation.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationDto;

import java.util.List;

public interface CompilationService {
    List<CompilationDto> getCompilations(Boolean pinned, Pageable pageable);

    CompilationDto getCompById(Long compId);

    CompilationDto createComp(NewCompilationDto newCompilationDto);

    void deleteCompById(Long compId);

    CompilationDto updateComp(UpdateCompilationDto updateCompilationDto, Long compId);
}
