package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.service.CategoryService;

import java.util.List;

/**
 * Класс публичного (доступна без регистрации любому пользователю сети) контроллера
 * для работы с сервисом категорий
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class PublicCategoriesController {
    /**
     * Поле сервис для работы с хранилищем категорий
     */
    private final CategoryService categoryService;

    /**
     * Метод получения любым пользователем списка всех категорий из хранилища сервиса через запрос
     *
     * @param from - индекс первого элемента, начиная с 0
     * @param size - количество элементов для отображения
     * @return список объектов CategoryDto {@link CategoryDto}
     */
    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") Integer from,
                                           @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET: получение категорий с параметрами: {}, {}", from, size);
        return categoryService.getCategories(PageRequest.of(from / size, size));
    }

    /**
     * Метод получения информации о категории из хранилища сервиса по идентификатору через запрос
     *
     * @param catId - идентификатор категории
     * @return копию объекта CategoryDto {@link CategoryDto}
     */
    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable Long catId) {
        log.info("GET: получение категории по идентификатору {}", catId);
        return categoryService.getCatById(catId);
    }
}
