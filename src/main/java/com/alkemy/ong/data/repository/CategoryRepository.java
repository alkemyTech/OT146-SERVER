package com.alkemy.ong.data.repository;

import java.util.List;

import com.alkemy.ong.data.entity.CategoryEntity;

import org.springframework.data.jpa.repository.JpaRepository;



public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAll();
    
    
}
