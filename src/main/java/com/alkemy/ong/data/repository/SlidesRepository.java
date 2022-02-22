package com.alkemy.ong.data.repository;

import com.alkemy.ong.data.entity.SlidesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlidesRepository extends CrudRepository<SlidesEntity, Long> {
}
