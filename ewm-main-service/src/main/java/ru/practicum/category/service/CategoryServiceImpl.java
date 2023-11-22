package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.error.exception.CategoryNotFoundException;
import ru.practicum.error.exception.ConflictException;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.model.Event;

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
    public CategoryDto createCat(CategoryDto categoryDto) {
        log.info("Создание категории {}", categoryDto);
        return categoryMapper.toCategoryDto(categoryRepository.save(categoryMapper.toCategory(categoryDto)));
    }

    @Transactional
    public CategoryDto updateCat(Long catId, CategoryDto categoryDto) {
        if (!categoryRepository.existsById(catId)) {
            throw new CategoryNotFoundException(String.format("Category " +
                    "with id=%d was not found", catId), "The required object was not found.");
        }
        return categoryMapper.toCategoryDto(categoryRepository
                .save(categoryMapper.toCategory(categoryDto)));
    }

    public void deleteCatById(Long catId) {
        if (!categoryRepository.existsById(catId)) {
            throw new CategoryNotFoundException(String.format("Category " +
                    "with id=%d was not found", catId), "The required object was not found.");
        }

        Event event = eventRepository.findFirstWhereCategoryIdIs(catId).orElseThrow(() ->
                new ConflictException("The category is not empty",
                        "For the requested operation the conditions are not met."));
        categoryRepository.deleteById(catId);
    }

    public List<CategoryDto> getCategories(PageRequest pageRequest) {
        List<Category> categories = categoryRepository.findAllOrderById(pageRequest);
        return categories.stream()
                .map(categoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    public CategoryDto getCatById(Long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new CategoryNotFoundException(String
                        .format("Category with id=%d was not found", catId),
                        "The required object was not found."));
        return categoryMapper.toCategoryDto(category);
    }
}
