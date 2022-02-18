package com.alkemy.ong.data.repository;

import com.alkemy.ong.data.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News,Long> {
}
