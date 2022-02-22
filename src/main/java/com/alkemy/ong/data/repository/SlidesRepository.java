package com.alkemy.ong.data.repository;

import com.alkemy.ong.data.entity.SlidesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlidesRepository extends JpaRepository<SlidesEntity, Long> {
}
