package com.alkemy.ong.data.respository;

import com.alkemy.ong.data.models.Testimonial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestimonialRepository extends CrudRepository<Testimonial, Long> {
}
