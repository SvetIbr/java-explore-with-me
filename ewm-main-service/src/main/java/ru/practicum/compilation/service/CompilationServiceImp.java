package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.repository.CompilationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompilationServiceImp implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    public List<CompilationDto> getCompilations(Boolean pinned, PageRequest of) {

    }

    public CompilationDto getCompById(Long compId) {

    }

    public CompilationDto createComp(NewCompilationDto newCompilationDto) {

    }

    public void deleteCompById(Long compId){

    }

    public CompilationDto updateComp(NewCompilationDto newCompilationDto, Long compId){

    }

}
