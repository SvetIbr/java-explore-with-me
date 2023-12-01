package ru.practicum.category.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCat(NewCategoryDto newCategoryDto);

    CategoryDto updateCat(Long catId, CategoryDto categoryDto);

    void deleteCatById(Long catId);

    List<CategoryDto> getCategories(Pageable pageable);

    CategoryDto getCatById(Long catId);
}
