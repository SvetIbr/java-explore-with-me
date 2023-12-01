package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.service.CategoryService;

import javax.validation.Valid;

/**
 * Класс административного (для администраторов сервиса) контроллера для работы с сервисом категорий
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
class AdminCategoriesController {
    /**
     * Поле сервис для работы с хранилищем категорий
     */
    private final CategoryService categoryService;

    /**
     * Метод добавления категории в хранилище сервиса через запрос
     *
     * @param newCategoryDto {@link NewCategoryDto}
     * @return {@link CategoryDto} с добавленным id и код ответа API 201
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCat(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        log.info("POST: создание категории с параметрами: {}", newCategoryDto);
        return categoryService.createCat(newCategoryDto);
    }

    /**
     * Метод обновления информации о категории в хранилище сервиса через запрос
     *
     * @param catId       - идентификатор категории
     * @param categoryDto {@link CategoryDto} - данные для обновления
     * @return копию объекта categoryDto с обновленными полями
     */
    @PatchMapping("/{catId}")
    public CategoryDto updateCatById(@PathVariable Long catId, @Valid @RequestBody CategoryDto categoryDto) {
        log.info("PATCH: обновление категории по идентификатору {} " +
                "с параметрами: {}", catId, categoryDto);
        return categoryService.updateCat(catId, categoryDto);
    }

    /**
     * Метод удаления категории из хранилища сервиса по идентификатору через запрос
     *
     * @param catId - идентификатор категории
     */
    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCatById(@PathVariable Long catId) {
        log.info("DELETE: удаление категории по идентификатору {}", catId);
        categoryService.deleteCatById(catId);
    }
}
