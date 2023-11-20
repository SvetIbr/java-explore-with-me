package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.service.CategoryService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class PublicCategoriesController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(required = false,
            defaultValue = "0") Integer from,
                                           @RequestParam(required = false,
                                                   defaultValue = "10") Integer size) {
        log.info("GET: получение категорий с параетрами: {}, {}", from, size);
        return categoryService.getCategories(PageRequest.of(from, size));
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable Long catId) {
        log.info("GET: получение категории по идентификатору {}", catId);
        return categoryService.getCatById(catId);
    }
}
