package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.service.CategoryService;

import javax.validation.Valid;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
 class AdminCategoriesController {
   private final CategoryService categoryService;
    @PostMapping
    public CategoryDto createCat(@Valid @RequestBody CategoryDto categoryDto) {
       log.info("POST: создание категории с параметрами: {}", categoryDto);
       return categoryService.createCat(categoryDto);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCatById(@PathVariable Long catId, @RequestBody @Valid CategoryDto categoryDto) {
       log.info("PATCH: обновление категории по идентификатору {} " +
               "с параметрами: {}", catId, categoryDto);
       return categoryService.updateCat(catId, categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCatById(@PathVariable Long catId) {
       log.info("DELETE: удаление категории по идентификатору {}", catId);
       categoryService.deleteCatById(catId);
    }
}
