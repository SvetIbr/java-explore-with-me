package ru.practicum.category.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

import java.util.List;

/**
 * Интерфейс сервиса категорий
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
public interface CategoryService {
    /**
     * Метод добавления категории в хранилище
     *
     * @param newCategoryDto {@link NewCategoryDto}
     * @return {@link CategoryDto} с добавленным id и код ответа API 201
     */
    CategoryDto createCat(NewCategoryDto newCategoryDto);

    /**
     * Метод обновления информации о категории в хранилище
     *
     * @param catId       - идентификатор категории
     * @param categoryDto {@link CategoryDto} - данные для обновления
     * @return копию объекта categoryDto с обновленными полями
     */
    CategoryDto updateCat(Long catId, CategoryDto categoryDto);

    /**
     * Метод удаления категории из хранилища по идентификатору
     *
     * @param catId - идентификатор категории
     */
    void deleteCatById(Long catId);

    /**
     * Метод получения любым пользователем списка всех категорий из хранилища
     *
     * @return список объектов CategoryDto {@link CategoryDto}
     */
    List<CategoryDto> getCategories(Pageable pageable);

    /**
     * Метод получения информации о категории из хранилища по идентификатору
     *
     * @param catId - идентификатор категории
     * @return копию объекта CategoryDto {@link CategoryDto}
     */
    CategoryDto getCatById(Long catId);
}
