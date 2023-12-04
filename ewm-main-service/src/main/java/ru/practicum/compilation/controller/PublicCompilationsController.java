package ru.practicum.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.service.CompilationService;

import java.util.List;

/**
 * Класс публичного (доступна без регистрации любому пользователю сети) контроллера
 * для работы с сервисом подборок событий
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
public class PublicCompilationsController {
    /**
     * Поле сервис для работы с хранилищем подборок событий
     */
    private final CompilationService compilationService;

    /**
     * Метод получения любым пользователем списка всех подборок из хранилища сервиса через запрос
     *
     * @param pinned - закреплена ли подборка на главной странице сайта
     * @param from   - индекс первого элемента, начиная с 0
     * @param size   - количество элементов для отображения
     * @return список объектов CompilationDto {@link CompilationDto}
     */
    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                @RequestParam(defaultValue = "0") Integer from,
                                                @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET: Запрос за получение подборок " +
                "событий с параметрами: {}, {}, {}", pinned, from, size);
        return compilationService.getCompilations(pinned, PageRequest.of(from / size, size));
    }

    /**
     * Метод получения информации о подборке событий из хранилища сервиса по идентификатору через запрос
     *
     * @param compId - идентификатор подборки
     * @return копию объекта CompilationDto {@link CompilationDto}
     */
    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable Long compId) {
        log.info("GET: Запрос за получение подборки " +
                "событий по идентификатору: {}", compId);
        return compilationService.getCompById(compId);
    }
}
