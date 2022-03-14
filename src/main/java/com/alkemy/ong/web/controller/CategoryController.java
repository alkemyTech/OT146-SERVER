package com.alkemy.ong.web.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.alkemy.ong.domain.Category.Category;
import com.alkemy.ong.domain.Category.CategoryService;
import com.alkemy.ong.web.exceptions.BadRequestException;
import com.alkemy.ong.web.utils.PageResponse;
import com.alkemy.ong.web.utils.PageUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiModelProperty;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/categories")
    public List<CategoryDto> findAll() {
        List<Category> categories = categoryService.findAll();

        return categories.stream().map(category -> toDto(category)).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(params = {"page"})
    ResponseEntity<PageResponse<CategoryDto>> findAllByPage(@RequestParam(name = "page") Integer page) {
        if (page < 0)
            throw new BadRequestException("Page not found");
        List<CategoryDto> catDto = categoryService.findAllByPage(page, PageUtils.PAGE_SIZE).stream().map(category -> toDto(category)).collect(Collectors.toList());

        PageResponse<CategoryDto> pr = new PageResponse<>(catDto, "/categories/page", page, PageUtils.PAGE_SIZE);
        return ResponseEntity.status(HttpStatus.OK).body(pr);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> show(@PathVariable Long id) {

        CategoryDto categoryDto = toDto(categoryService.findById(id));

        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryService.create(toCategory(categoryDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(category));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Long id, @Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryService.update(id, toCategory(categoryDto));
        return ResponseEntity.status(HttpStatus.OK).body(toDto(category));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private CategoryDto toDto(Category category) {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
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

        @ApiModelProperty(example = "1")
        private Long id;

        @ApiModelProperty(required = true, example = "RRHH")
        private String name;

        @ApiModelProperty(example = "Categoria designada para contenido de RRHH.")
        private String description;

        @ApiModelProperty(required = true)
        private Boolean deleted;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

}
