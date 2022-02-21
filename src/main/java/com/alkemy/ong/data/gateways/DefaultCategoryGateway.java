package com.alkemy.ong.data.gateways;


import java.util.List;

import com.alkemy.ong.data.entity.CategoryEntity;
import com.alkemy.ong.data.repository.CategoryRepository;
import com.alkemy.ong.domain.Category.Category;
import com.alkemy.ong.domain.Category.CategoryGateway;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class DefaultCategoryGateway implements CategoryGateway {

    private CategoryRepository categoryRepo; 

    public DefaultCategoryGateway(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }


    @Override
    public List<Category> findAll() {
        List<CategoryEntity> categoryEntity = categoryRepo.findAll();

        return categoryEntity.stream().map(ToModel).
    }


    
}
