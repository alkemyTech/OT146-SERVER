package com.alkemy.ong.domain.categories;

import java.util.List;


public interface CategoryGateway {
    
    List<Category> findAll();
    Category findById(Long id);
    Category create(Category category);
    Category update(Long id, Category category);
    void delete(Long id);
    List<Category> findAllByPage(int page, int size);
}
