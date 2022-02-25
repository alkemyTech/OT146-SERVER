package com.alkemy.ong.domain.testimonial;

import com.alkemy.ong.domain.testimonial.Testimonial;

public interface TestimonialGateway {
    public Testimonial create(Testimonial testimonial);
    public Testimonial update(Long id, Testimonial testimonial);
    public void delete(Long id);
}
