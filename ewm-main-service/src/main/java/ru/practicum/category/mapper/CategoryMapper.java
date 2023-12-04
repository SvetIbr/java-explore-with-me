package ru.practicum.category.mapper;

import org.mapstruct.Mapper;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.model.Category;

/**
 * Mapper-класс для преобразования объектов сервиса категорий
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    /**
     * Метод преобразования объекта CategoryDto в Category
     *
     * @param categoryDto {@link CategoryDto}
     * @return {@link Category}
     */
    Category toCategory(CategoryDto categoryDto);

    /**
     * Метод преобразования объекта Category в CategoryDto
     *
     * @param category {@link Category}
     * @return {@link CategoryDto}
     */
    CategoryDto toCategoryDto(Category category);

    /**
     * Метод преобразования объекта NewCategoryDto в Category
     *
     * @param newCategoryDto {@link NewCategoryDto}
     * @return {@link Category}
     */
    Category toCategory(NewCategoryDto newCategoryDto);
}
