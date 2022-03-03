package com.alkemy.ong.web.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.alkemy.ong.domain.Category.Category;
import com.alkemy.ong.domain.Category.CategoryService;
import com.alkemy.ong.web.exceptions.BadRequestException;
import com.alkemy.ong.web.utils.PageResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final int PAGE_SIZE = 10;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<CategoryDto> findAll() {
        List<Category> categories = categoryService.findAll();
        
        return categories.stream().map(category -> toDto(category)).collect(Collectors.toList());
    }

    @GetMapping(params = {"page"})
    ResponseEntity<PageResponse<CategoryDto>> lisAllByPage(@RequestParam(name = "page") Integer page) {
        if(page < 0)
            throw new BadRequestException("Page not found");
        List<CategoryDto> catDto = categoryService.findAllByPage(page, PAGE_SIZE).stream().map(category -> toDto(category)).collect(Collectors.toList());

        PageResponse<CategoryDto> pr= new PageResponse<>(catDto, "/categories", page, PAGE_SIZE);
        return ResponseEntity.status(HttpStatus.OK).body(pr);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> show(@PathVariable Long id) {
       
        CategoryDto categoryDto = toDto(categoryService.findById(id));
   
        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);          
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryService.create(toCategory(categoryDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(category));
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Long id, @Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryService.update(id, toCategory(categoryDto));
        return ResponseEntity.status(HttpStatus.OK).body(toDto(category));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { 
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private CategoryDto toDto(Category category) {
        CategoryDto categoryDto = CategoryDto.builder()
        .id(category.getId())
        .name(category.getName())
        .build();
    
        return categoryDto;
    }

    private Category toCategory(CategoryDto categoryDto) {
        Category category = Category.builder()
        .name(categoryDto.getName())
        .description(categoryDto.getDescription())
        .deleted(categoryDto.getDeleted())
        .createdAt(categoryDto.getCreatedAt())
        .updatedAt(categoryDto.getUpdatedAt())
        .build();

        return category;
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
        private Boolean deleted;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    } 
    
}
