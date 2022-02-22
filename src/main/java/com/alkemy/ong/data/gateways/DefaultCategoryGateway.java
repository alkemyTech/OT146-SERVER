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

    private CategoryRepository categoryRepo; 

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

    private Category toModel(CategoryEntity categoryEntity) {
        Category category = new Category();
        category.setId(categoryEntity.getId());
        category.setName(categoryEntity.getName());
        category.setDescription(categoryEntity.getDescription());
        category.setCreatedAt(categoryEntity.getCreatedAt());
        category.setUpdatedAt(categoryEntity.getUpdatedAt());

        return category;
    }


    
}
