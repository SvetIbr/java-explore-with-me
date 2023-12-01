package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.error.exception.ConflictException;
import ru.practicum.error.exception.NotFoundException;
import ru.practicum.error.exception.UniqueNameCategoryException;
import ru.practicum.event.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryDto createCat(NewCategoryDto newCategoryDto) {
        if(categoryRepository.existsCategoryByName(newCategoryDto.getName())) {
            throw new UniqueNameCategoryException("Категория с названием " + newCategoryDto.getName() +
                    " уже существует!");
        }
        Category category = categoryRepository.save(categoryMapper.toCategory(newCategoryDto));
        return categoryMapper.toCategoryDto(category);
    }

    @Transactional
    public CategoryDto updateCat(Long catId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(catId).orElseThrow(() ->
                new NotFoundException(String.format("Category " +
                "with id=%d was not found", catId)));

        if (categoryDto.getName() == null
                || categoryDto.getName().isEmpty()
                || categoryDto.getName().isBlank()
                || categoryDto.getName().equals(category.getName())) {
            return categoryMapper.toCategoryDto(category);
        }

        if(categoryRepository.existsCategoryByName(categoryDto.getName())) {
            throw new UniqueNameCategoryException("Категория с названием " + categoryDto.getName() +
                    " уже существует!");
        }

        category.setName(categoryDto.getName());
        category = categoryRepository.save(category);
        return categoryMapper.toCategoryDto(category);
    }


    public void deleteCatById(Long catId) {
        if (!categoryRepository.existsById(catId)) {
            throw new NotFoundException(String.format("Category " +
                    "with id=%d was not found", catId));
        }

        if (eventRepository.existsByCategoryId(catId)) {
                throw new ConflictException("Category must not be assigned to any event.");
        }
        categoryRepository.deleteById(catId);
    }

    public List<CategoryDto> getCategories(Pageable pageable) {
        List<CategoryDto> categories =  categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toCategoryDto)
                .collect(Collectors.toList());
        return (!categories.isEmpty()) ? categories : new ArrayList<>();
    }

    public CategoryDto getCatById(Long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String
                        .format("Category with id=%d was not found", catId)));
        return categoryMapper.toCategoryDto(category);
    }
}
