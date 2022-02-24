package com.alkemy.ong.web.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


import com.alkemy.ong.domain.Category.Category;
import com.alkemy.ong.domain.Category.CategoryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/ong")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<CategoryDto> findAll() {
        List<Category> categories = categoryService.findAll();
        
        return categories.stream().map(category -> toDto(category)).collect(Collectors.toList());
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> show(@PathVariable Long id) {
       
        CategoryDto categoryDto = toDto(categoryService.findById(id));
   
        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);          
    }

    private CategoryDto toDto(Category category) {
        CategoryDto categoryDto = CategoryDto.builder()
        .id(category.getId())
        .name(category.getName())
        .build();
    
        return categoryDto;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class CategoryDto { 

        private Long id;
        private String name;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    } 
    
}
