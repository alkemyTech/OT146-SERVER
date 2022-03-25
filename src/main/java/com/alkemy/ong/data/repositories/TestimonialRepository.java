package com.alkemy.ong.data.repositories;

import com.alkemy.ong.data.entities.TestimonialEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestimonialRepository extends PagingAndSortingRepository<TestimonialEntity, Long> {
    public Page<TestimonialEntity> findByDeleted(boolean deleted, Pageable pageable);
}
