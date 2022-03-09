package com.alkemy.ong.data.repository;

import com.alkemy.ong.data.entity.NewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends PagingAndSortingRepository<NewsEntity, Long> {
    public Page<NewsEntity> findByDeleted(boolean deleted, Pageable pageable);

    Optional<NewsEntity> findById(Long id);
}
