package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.error.exception.CompilationNotFoundException;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImp implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventRepository eventRepository;

    public List<CompilationDto> getCompilations(Boolean pinned, PageRequest pageRequest) {
        return compilationRepository.findAllWherePinnedIs(pinned, pageRequest)
                .stream()
                .map(compilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    public CompilationDto getCompById(Long compId) {
        return compilationMapper.toCompilationDto(compilationRepository.findById(compId).orElseThrow(
                () -> new CompilationNotFoundException(String
                        .format("Compilation with id=%d was not found", compId),
                        "The required object was not found.")));
    }

    public CompilationDto createComp(NewCompilationDto newCompilationDto) {
        return compilationMapper.toCompilationDto(compilationRepository
                .save(compilationMapper.toCompilation(newCompilationDto)));
    }

    public void deleteCompById(Long compId) {
        if (!compilationRepository.existsById(compId)) {
            throw new CompilationNotFoundException(String
                    .format("Compilation with id=%d was not found", compId),
                    "The required object was not found.");
        }
        compilationRepository.deleteById(compId);
    }

    public CompilationDto updateComp(NewCompilationDto newCompilationDto, Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new CompilationNotFoundException(String
                        .format("Compilation with id=%d was not found", compId),
                        "The required object was not found."));

        List<Event> events = eventRepository.findAllWhereEventIdIn(newCompilationDto.getEvents());

        if (newCompilationDto.getPinned() != null) {
            compilation.setPinned(newCompilationDto.getPinned());
        }
        if (newCompilationDto.getTitle() != null) {
            compilation.setTitle(newCompilationDto.getTitle());
        }
        compilation.setEvents(events);
        return compilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }
}


