package com.alkemy.ong.domain.Category;

import java.util.List;

public interface CategoryGateway {
    
    List<Category> findAll();
    Category findById(Long id);
    Category create(Category category);
    Category update(Long id, Category category);
}
