package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.testimonials.Testimonial;
import com.alkemy.ong.domain.testimonials.TestimonialService;
import com.alkemy.ong.web.exceptions.BadRequestException;
import com.alkemy.ong.web.utils.PageResponse;
import io.swagger.annotations.*;
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
@Api(value="testimonials")
public class TestimonialController {

    private final int PAGE_SIZE = 10;

    private final TestimonialService testimonialService;

    public TestimonialController(TestimonialService testimonialService) {
        this.testimonialService = testimonialService;
    }


    @ApiOperation(value = "Save Testimonial")
    @PostMapping
    public ResponseEntity<TestimonialDTO> save(@Valid @RequestBody  TestimonialDTO dto) {
        Testimonial testimonial = testimonialService.save(toDomain(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(testimonial));
    }


    @ApiOperation(value = "Update Testimonial")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TestimonialDTO> update(@PathVariable Long id, @Valid @RequestBody  TestimonialDTO dto) {
        Testimonial testimonial = testimonialService.update(id, toDomain(dto));
        return ResponseEntity.status(HttpStatus.OK).body(toDto(testimonial));
    }


    @ApiOperation(value = "Delete Testimonial")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        testimonialService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "List Testimonial by Page")
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
    @ApiModel
    public static class TestimonialDTO {
        @ApiModelProperty( name = "id", example = "1")
        private Long id;

        @ApiModelProperty( name = "name",
                value = "Name of the Testimonial",
                required = true,
                example = "Martin Suarez")
        @NotNull @NotBlank @NotEmpty
        private String name;

        @ApiModelProperty( name = "image",
                value = "Image url",
                example = "https://s3.us-east-1.amazonaws.com/cohorte-febrero-b35bfd02/2022-03-04T20:09:32.426-foto.jpg")
        private String image;

        @ApiModelProperty( name = "content",
                value = "Content",
                required = true,
                example = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.")
        @NotNull @NotBlank @NotEmpty
        private String content;
    }


}

