package ru.practicum.compilation.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationDto;

import java.util.List;

/**
 * Интерфейс сервиса подборок событий
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
public interface CompilationService {
    /**
     * Метод получения любым пользователем списка всех подборок из хранилища
     *
     * @param pinned - закреплена ли подборка на главной странице сайта
     * @return список объектов CompilationDto {@link CompilationDto}
     */
    List<CompilationDto> getCompilations(Boolean pinned, Pageable pageable);

    /**
     * Метод получения информации о подборке событий из хранилища по идентификатору
     *
     * @param compId - идентификатор подборки
     * @return копию объекта CompilationDto {@link CompilationDto}
     */
    CompilationDto getCompById(Long compId);

    /**
     * Метод добавления подборки событий в хранилище
     *
     * @param newCompilationDto {@link NewCompilationDto}
     * @return {@link CompilationDto} с добавленным id и код ответа API 201
     */
    CompilationDto createComp(NewCompilationDto newCompilationDto);

    /**
     * Метод удаления подборки событий из хранилища по идентификатору
     *
     * @param compId - идентификатор подборки
     */
    void deleteCompById(Long compId);

    /**
     * Метод обновления информации о подборке событий в хранилище
     *
     * @param compId               - идентификатор подборки
     * @param updateCompilationDto {@link UpdateCompilationDto} - данные для обновления
     * @return {@link CompilationDto} с обновленными полями
     */
    CompilationDto updateComp(UpdateCompilationDto updateCompilationDto, Long compId);
}
