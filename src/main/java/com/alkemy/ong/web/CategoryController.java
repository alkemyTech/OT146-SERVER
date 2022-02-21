package com.alkemy.ong.web;

import java.time.LocalDateTime;
import java.util.List;

import com.alkemy.ong.domain.Category.Category;
import com.alkemy.ong.domain.Category.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ong")
public class CategoryController {



    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public List<CategoryDto> findAll() {
        List<Category> category = categoryService.findAll();
        
        return category.stream().map(mapper).;
    }
    




    public static class CategoryDto { 

        private Long id;
        private String name;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    
        public Long getId() {
            return this.id;
        }
    
        public void setId(Long id) {
            this.id = id;
        }
    
        public String getName() {
            return this.name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getDescription() {
            return this.description;
        }
    
        public void setDescription(String description) {
            this.description = description;
        }
    
        public LocalDateTime getCreatedAt() {
            return this.createdAt;
        }
    
        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }
    
        public LocalDateTime getUpdatedAt() {
            return this.updatedAt;
        }
    
        public void setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
        }
    } 
    
}
