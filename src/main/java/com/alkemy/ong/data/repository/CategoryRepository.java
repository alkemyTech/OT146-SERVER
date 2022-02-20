package com.alkemy.ong.data.repository;

import com.alkemy.ong.data.entity.CategoryEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    @Query(value = "SELECT c.name FROM Category c")
    Iterable<Object[]> getAllCategories();
    
    
}
