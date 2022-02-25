package com.alkemy.ong.data.repository;

import java.util.List;

import com.alkemy.ong.data.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAll();   
}
