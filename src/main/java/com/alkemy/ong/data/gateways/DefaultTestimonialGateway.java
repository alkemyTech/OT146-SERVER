package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entity.TestimonialEntity;
import com.alkemy.ong.data.repository.TestimonialRepository;
import com.alkemy.ong.domain.testimonial.TestimonialGateway;
import com.alkemy.ong.domain.testimonial.Testimonial;
import com.alkemy.ong.web.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultTestimonialGateway implements TestimonialGateway {

    private final TestimonialRepository repository;

    public DefaultTestimonialGateway(TestimonialRepository repository) {
        this.repository = repository;
    }

    @Override
    public Testimonial create(Testimonial testimonial) {
        TestimonialEntity entity = toEntity(testimonial);
        return toModel(repository.save(entity));
    }

    @Override
    public Testimonial update( Long id, Testimonial testimonial) {
            TestimonialEntity testimonialEntity = repository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("non-existent testimony to update"));
            testimonialEntity.setContent(testimonial.getContent());
            testimonialEntity.setImage(testimonial.getImage());
            testimonialEntity.setName(testimonial.getName());
            return toModel(repository.save(testimonialEntity));
    }

    @Override
    public List<Testimonial> listByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByDeleted(false, pageable)
                .stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Testimonial toModel(TestimonialEntity testimonialEntity){
        return Testimonial.builder()
                .id(testimonialEntity.getId())
                .name(testimonialEntity.getName())
                .image(testimonialEntity.getImage())
                .content(testimonialEntity.getContent())
                .build();
    }

    public TestimonialEntity toEntity(Testimonial testimonial){
        return TestimonialEntity.builder()
                .name(testimonial.getName())
                .content(testimonial.getContent())
                .image(testimonial.getImage())
                .build();
    }
}
