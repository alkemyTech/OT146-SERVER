package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.testimonial.Testimonial;
import com.alkemy.ong.domain.testimonial.TestimonialService;
import com.alkemy.ong.web.exceptions.BadRequestException;
import com.alkemy.ong.web.utils.PageResponse;
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

    private final int PAGE_SIZE = 10;

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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        testimonialService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping(params = {"page"})
    ResponseEntity<PageResponse<TestimonialDTO>> lisAllByPage(@RequestParam(name = "page") Integer page){
        if(page < 0)
            throw new BadRequestException("Page index must not be less than zero");
        List<TestimonialDTO> testimonialDTOS = testimonialService.listByPage(page, PAGE_SIZE)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        PageResponse<TestimonialDTO> pageResponse = new PageResponse<>(testimonialDTOS, "/testimonials", page, PAGE_SIZE);
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


}

