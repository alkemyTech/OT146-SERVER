package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.testimonial.Testimonial;
import com.alkemy.ong.domain.testimonial.TestimonialService;
import com.alkemy.ong.web.exceptions.BadRequestException;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {

    private final TestimonialService testimonialService;

    public TestimonialController(TestimonialService testimonialService) {
        this.testimonialService = testimonialService;
    }

    @PostMapping
    public ResponseEntity<TestimonialDTO> save(@Valid @RequestBody  TestimonialDTO dto) {
        Testimonial testimonial = testimonialService.save(toDomain(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(testimonial));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TestimonialDTO> update(@PathVariable Long id, @Valid @RequestBody  TestimonialDTO dto) {
        Testimonial testimonial = testimonialService.update(id, toDomain(dto));
        return ResponseEntity.status(HttpStatus.OK).body(toDto(testimonial));
    }


    @GetMapping(params = {"page"})
    ResponseEntity<Page> lisAllByPage(@RequestParam(name = "page") Integer page){
        if(page < 0)
            throw new BadRequestException("Page index must not be less than zero");
        List<TestimonialDTO> testimonialDTOS = testimonialService.listByPage(page)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        Page pageResponse = Page.builder()
                .content(testimonialDTOS)
                .nextPage("/testimonials?page=" + (page +1))
                .previousPage((page > 0)? ("/testimonials?page=" + (page -1)) : "")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(pageResponse);
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
    @Builder
    @Getter @Setter
    public static class Page{
        private List<TestimonialDTO> content;
        private String nextPage;
        private String previousPage;
    }
}

