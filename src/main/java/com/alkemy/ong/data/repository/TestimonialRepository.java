package com.alkemy.ong.data.repository;

import com.alkemy.ong.data.entity.TestimonialEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestimonialRepository extends CrudRepository<TestimonialEntity, Long> {
}
