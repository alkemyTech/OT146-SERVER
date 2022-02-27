package com.alkemy.ong.domain.testimonial;

import java.util.List;

public interface TestimonialGateway {
    public Testimonial create(Testimonial testimonial);
    public Testimonial update(Long id, Testimonial testimonial);
    public List<Testimonial> listByPage(int page, int size);
}
