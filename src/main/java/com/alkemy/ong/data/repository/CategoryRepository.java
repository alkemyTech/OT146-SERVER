package com.alkemy.ong.data.repository;


import com.alkemy.ong.data.entity.CategoryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAll();
    Page<CategoryEntity> findByDeleted(boolean deleted, Pageable pageable);
}
