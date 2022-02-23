package com.alkemy.ong.data.repository;

import com.alkemy.ong.data.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity,Long> {
}
