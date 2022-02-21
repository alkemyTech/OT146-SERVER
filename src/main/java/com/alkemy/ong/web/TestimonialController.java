package com.alkemy.ong.web;

import com.alkemy.ong.domain.models.Testimonial;
import com.alkemy.ong.domain.services.TestimonialService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {

    private final TestimonialService testimonialService;

    public TestimonialController(TestimonialService testimonialService) {
        this.testimonialService = testimonialService;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> save(@Valid @RequestBody  TestimonialDTO dto) {
        Testimonial testimonial = testimonialService.save(toDomain(dto));

        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(testimonial));
    }

    private Testimonial toDomain(TestimonialDTO dto){
        return Testimonial.builder()
                .id(null)
                .name(dto.getName())
                .image(dto.getImage())
                .content(dto.getContent())
                .build();
    }


    private TestimonialDTO toDto(Testimonial testimonial){
        return TestimonialDTO.builder()
                .id(testimonial.getId())
                .name(testimonial.getName())
                .image(testimonial.getImage())
                .content(testimonial.getContent())
                .build();
    }

    @Builder
    @Getter @Setter @AllArgsConstructor
    public static class TestimonialDTO {
        private Long id;
        @NotNull @NotBlank @NotEmpty
        private String name;
        private String image;
        @NotNull @NotBlank @NotEmpty
        private String content;
    }
}

