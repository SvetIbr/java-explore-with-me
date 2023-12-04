package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationDto;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.error.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.constants.Constants.COMPILATION_NOT_FOUND_MSG;

@Service
@RequiredArgsConstructor
public class CompilationServiceImp implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    @Transactional(readOnly = true)
    public List<CompilationDto> getCompilations(Boolean pinned, Pageable pageable) {
        Page<Compilation> result;
        if (pinned != null) {
            result = compilationRepository.findAllByPinned(pinned, pageable);
        } else {
            result = compilationRepository.findAll(pageable);
        }
        return result
                .stream()
                .map(compilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CompilationDto getCompById(Long compId) {
        return compilationMapper.toCompilationDto(compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException(String.format(COMPILATION_NOT_FOUND_MSG, compId))));
    }

    @Transactional
    public CompilationDto createComp(NewCompilationDto newCompilationDto) {
        return compilationMapper.toCompilationDto(compilationRepository.save(compilationMapper
                .toCompilation(newCompilationDto)));
    }

    @Transactional
    public void deleteCompById(Long compId) {
        if (!compilationRepository.existsById(compId)) {
            throw new NotFoundException(String.format(COMPILATION_NOT_FOUND_MSG, compId));
        }
        compilationRepository.deleteById(compId);
    }

    @Transactional
    public CompilationDto updateComp(UpdateCompilationDto updateCompilationDto, Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(String
                        .format(COMPILATION_NOT_FOUND_MSG, compId)));

        compilation = compilationMapper.update(updateCompilationDto, compilation);

        return compilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }
}


