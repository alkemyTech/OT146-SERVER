package com.alkemy.ong.data.gateways;


import java.util.List;
import java.util.stream.*;

import com.alkemy.ong.data.entity.CategoryEntity;
import com.alkemy.ong.data.repository.CategoryRepository;
import com.alkemy.ong.domain.Category.Category;
import com.alkemy.ong.domain.Category.CategoryGateway;


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
    public Category findById(Long id) {
        
        return toModel(categoryRepo.findById(id).orElse(null));
    }

    private Category toModel(CategoryEntity categoryEntity) {
        Category category = Category.builder()
        .id(categoryEntity.getId())
        .name(categoryEntity.getName())
        .description(categoryEntity.getDescription())
        .createdAt(categoryEntity.getCreatedAt())
        .updatedAt(categoryEntity.getUpdatedAt())
        .build();
        
        return category;
    }


   
}
