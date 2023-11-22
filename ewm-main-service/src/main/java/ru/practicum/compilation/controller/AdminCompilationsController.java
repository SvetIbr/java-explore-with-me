package ru.practicum.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.service.CompilationService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationsController {
    private final CompilationService compilationService;
    @PostMapping
    public CompilationDto createComp(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("POST: создание подборки с параметрами: {}", newCompilationDto);
        return compilationService.createComp(newCompilationDto);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompById(@PathVariable Long compId,
                                    @Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("PATCH: обновление подборки " +
                "по идентификатору {} с новыми данными: {}", compId, newCompilationDto);
        return compilationService.updateComp(newCompilationDto, compId);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompById(@PathVariable Long compId) {
        log.info("DELETE: удаление подборки по идентификатору {}", compId);
        compilationService.deleteCompById(compId);
    }
}
