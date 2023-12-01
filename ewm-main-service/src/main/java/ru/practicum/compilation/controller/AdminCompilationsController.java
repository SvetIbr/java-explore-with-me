package ru.practicum.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationDto;
import ru.practicum.compilation.service.CompilationService;

import javax.validation.Valid;

/**
 * Класс административного (для администраторов сервиса) контроллера для работы с сервисом подборок событий
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationsController {
    /**
     * Поле сервис для работы с хранилищем подборок событий
     */
    private final CompilationService compilationService;

    /**
     * Метод добавления подборки событий в хранилище сервиса через запрос
     *
     * @param newCompilationDto {@link NewCompilationDto}
     * @return {@link CompilationDto} с добавленным id и код ответа API 201
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createComp(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("POST: создание подборки с параметрами: {}", newCompilationDto);
        return compilationService.createComp(newCompilationDto);
    }

    /**
     * Метод обновления информации о подборке событий в хранилище сервиса через запрос
     *
     * @param compId               - идентификатор подборки
     * @param updateCompilationDto {@link UpdateCompilationDto} - данные для обновления
     * @return {@link CompilationDto} с обновленными полями
     */
    @PatchMapping("/{compId}")
    public CompilationDto updateCompById(@PathVariable Long compId,
                                         @Valid @RequestBody UpdateCompilationDto updateCompilationDto) {
        log.info("PATCH: обновление подборки " +
                "по идентификатору {} с новыми данными: {}", compId, updateCompilationDto);
        return compilationService.updateComp(updateCompilationDto, compId);
    }

    /**
     * Метод удаления подборки событий из хранилища сервиса по идентификатору через запрос
     *
     * @param compId - идентификатор подборки
     */
    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompById(@PathVariable Long compId) {
        log.info("DELETE: удаление подборки по идентификатору {}", compId);
        compilationService.deleteCompById(compId);
    }
}
