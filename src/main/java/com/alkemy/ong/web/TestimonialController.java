package com.alkemy.ong.web;

import com.alkemy.ong.domain.models.Testimonial;
import com.alkemy.ong.domain.services.TestimonialService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {

    private final TestimonialService testimonialService;

    public TestimonialController(TestimonialService testimonialService) {
        this.testimonialService = testimonialService;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> save(@RequestBody TestimonialDTO dto) {
        try{
            if (dto == null)
                throw new Exception("not request content");
            if(dto.name == null || dto.name.equals(""))
                throw new Exception("name is invalid");
            if(dto.content == null || dto.content.equals(""))
                throw new Exception("content is invalid");

            Testimonial testimonial = testimonialService.save(convertToDomain(dto));

            TestimonialDTO testimonialOut = convertToDto(testimonial);

            return ResponseEntity.status(HttpStatus.OK).body(testimonialOut);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponseBasic(e.getMessage()));
        }
    }

    private Testimonial convertToDomain(TestimonialDTO dto){
        return Testimonial.builder()
                .id(null)
                .name(dto.getName())
                .image(dto.getImage())
                .content(dto.getContent())
                .build();
    }


    private TestimonialDTO convertToDto(Testimonial testimonial){
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
        private String name;
        private String image;
        private String content;
    }

    @Getter @Setter @AllArgsConstructor
    public static class ErrorResponseBasic{
        private String messaje;
    }

}

