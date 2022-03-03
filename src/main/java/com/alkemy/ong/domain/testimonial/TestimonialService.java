package com.alkemy.ong.domain.testimonial;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestimonialService {
    private final TestimonialGateway testimonialGateway;

    public TestimonialService(TestimonialGateway testimonialGateway) {
        this.testimonialGateway = testimonialGateway;
    }

    public Testimonial save(Testimonial testimonial){
        return testimonialGateway.create(testimonial);
    }

    public List<Testimonial> listByPage(int i, int size){
        return testimonialGateway.listByPage(i, size);
    }

    public Testimonial update(Long id, Testimonial testimonial){
        return testimonialGateway.update(id, testimonial);
    }

    public void delete(Long id) {
        testimonialGateway.delete(id);
    }
}
