package ru.practicum.compilation.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;

import java.util.List;

@Service
public class CompilationServiceImp implements CompilationService {
    public List<CompilationDto> getCompilations(Boolean pinned, PageRequest of) {

    }

    public CompilationDto getCompilationById(Long compId) {

    }

    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {

    }

    public void deleteCompilationById(Long compId){

    }

    public CompilationDto updateCompilation(NewCompilationDto newCompilationDto, Long compId){

    }

}
