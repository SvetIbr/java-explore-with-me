package ru.practicum.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.service.CompilationService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
public class PublicCompilationsController {
    private final CompilationService compilationService;
    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                @RequestParam(required = false,
                                                   defaultValue = "0") Integer from,
                                                @RequestParam(required = false,
                                                   defaultValue = "10") Integer size) {
        log.info("GET: Запрос за получение подборок " +
                "событий с параметрами: {}, {}, {}", pinned, from, size);
        return compilationService.getCompilations(pinned, PageRequest.of(from, size));
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompById(@PathVariable Long compId) {
        log.info("GET: Запрос за получение подборки " +
                "событий по идентификатору: {}", compId);
        return compilationService.getCompilationById(compId);
    }
}
