package com.alkemy.ong.data.gateways;


import java.util.List;
import java.util.stream.*;

import com.alkemy.ong.data.entity.CategoryEntity;
import com.alkemy.ong.data.repository.CategoryRepository;
import com.alkemy.ong.domain.Category.Category;
import com.alkemy.ong.domain.Category.CategoryGateway;
import com.alkemy.ong.web.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Component;

@Component
public class DefaultCategoryGateway implements CategoryGateway {

    private final CategoryRepository categoryRepo; 

    public DefaultCategoryGateway(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }


    @Override
    public List<Category> findAll() {
        List<CategoryEntity> categoryEntities = categoryRepo.findAll();

        return categoryEntities.stream()
                            .map(entity -> toModel(entity))
                            .collect(Collectors.toList());
    }

    @Override
    public List<Category> findAllByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryRepo.findByDeleted(false, pageable)
                .stream()
                .map(entity -> toModel(entity))
                .collect(Collectors.toList());
    }

    @Override
    public Category findById(Long id) {
    
        return toModel(categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("id: " + id + " not found"))); 
    }
 
    @Override
    public Category create(Category category) {
        CategoryEntity ce = toEntity(category);
        return toModel(categoryRepo.save(ce));
    }

    @Override
    public Category update(Long id, Category category) {

        CategoryEntity ce = categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("id: " + id + " not found"));
        ce.setName(category.getName());
        ce.setDescription(category.getDescription());
        ce.setDeleted(category.getDeleted());
        return toModel(categoryRepo.save(ce));
    }

    @Override
    public void delete(Long id) {
        CategoryEntity ce = categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("id: " + id + " not found")); 
        ce.setDeleted(true);
        categoryRepo.save(ce);
    }

    private Category toModel(CategoryEntity categoryEntity) {
        Category category = Category.builder()
        .id(categoryEntity.getId())
        .name(categoryEntity.getName())
        .description(categoryEntity.getDescription())
        .deleted(categoryEntity.getDeleted())
        .createdAt(categoryEntity.getCreatedAt())
        .updatedAt(categoryEntity.getUpdatedAt())
        .build();
        
        return category;
    }

    private CategoryEntity toEntity(Category category) {
        CategoryEntity categoryEntity = CategoryEntity.builder()
        .name(category.getName())
        .description(category.getDescription())
        .deleted(category.getDeleted())
        .createdAt(category.getCreatedAt())
        .updatedAt(category.getUpdatedAt())
        .build();

        return categoryEntity;
    }

}
