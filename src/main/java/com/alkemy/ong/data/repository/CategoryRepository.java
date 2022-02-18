package com.alkemy.ong.data.repository;

import com.alkemy.ong.data.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
