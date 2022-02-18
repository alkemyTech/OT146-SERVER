package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entity.TestimonialEntity;
import com.alkemy.ong.data.repository.TestimonialRepository;
import com.alkemy.ong.domain.gateways.TestimonialGateway;
import com.alkemy.ong.domain.models.Testimonial;
import org.springframework.stereotype.Component;

@Component
public class DefaultTestimonialGateway implements TestimonialGateway {

    private TestimonialRepository repository;

    public DefaultTestimonialGateway(TestimonialRepository repository) {
        this.repository = repository;
    }

    @Override
    public Testimonial create(Testimonial testimonial) {
        TestimonialEntity entity = convertAt(testimonial);
        return convertAt(repository.save(entity));
    }

    public Testimonial convertAt(TestimonialEntity testimonialEntity){
        return new Testimonial(
                testimonialEntity.getId(),
                testimonialEntity.getName(),
                testimonialEntity.getImage(),
                testimonialEntity.getContent());
    }

    public TestimonialEntity convertAt(Testimonial testimonial){
        TestimonialEntity entity = new TestimonialEntity();
        entity.setName(testimonial.getName());
        entity.setContent(testimonial.getContent());
        entity.setImage(testimonial.getImage());
        return entity;
    }
}
