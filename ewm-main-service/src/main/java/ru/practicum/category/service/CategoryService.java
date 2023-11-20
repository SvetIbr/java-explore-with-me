package ru.practicum.category.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCat(CategoryDto categoryDto);

    CategoryDto updateCat(Long catId, CategoryDto categoryDto);

    void deleteCatById(Long catId);

    List<CategoryDto> getCategories(PageRequest of);

    CategoryDto getCatById(Long catId);
}
