package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.slides.SimpleSlide;
import com.alkemy.ong.domain.slides.Slides;
import com.alkemy.ong.domain.slides.SlidesService;
import com.alkemy.ong.domain.slides.response.SlideShortResponse;
import com.alkemy.ong.domain.cloud.CloudService;
import com.alkemy.ong.web.controller.OrganizationController.OrganizationDto;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/slides")
public class SlidesController {
    private final SlidesService slidesService;
    private final CloudService cloudService;

    public SlidesController(SlidesService slidesService, CloudService cloudService) {

        this.slidesService = slidesService;
        this.cloudService = cloudService;
    }

    @GetMapping
    public ResponseEntity<List<SlideShortResponse>> getBriefSlides(){
        List<SlideShortResponse> returnValue = new ArrayList<>();

        List<SlidesDto> slides = slidesService.findAll()
                .stream()
                .map(slide -> toDto(slide))
                .collect(toList());

        for(SlidesDto slidesDto : slides){
            SlideShortResponse slidesModel = new SlideShortResponse();
            BeanUtils.copyProperties(slidesDto, slidesModel);
            returnValue.add(slidesModel);
        }

        return new ResponseEntity<>(returnValue, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SlidesDto> getDetailsById(@PathVariable Long id){
        
        SlidesDto slide = toDto(slidesService.findById(id));

        return new ResponseEntity<>(slide, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SlidesDto> createSlide(@Valid @RequestBody SimpleSlideDto slideBody) {

        SlidesDto slidesDto = toDto(slidesService.create(toSimpleDomain(slideBody)));

        return new ResponseEntity<>(slidesDto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<SlidesDto> updateSlide(@PathVariable Long id, @Valid @RequestBody SimpleSlideDto slideBody){

        SlidesDto slidesDto = toDto(slidesService.update(id, toSimpleDomain(slideBody)));

        return new ResponseEntity<>(slidesDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteSlide(@PathVariable Long id){
        slidesService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    public static SlidesDto toDto(Slides slides){
        return SlidesDto.builder()
                .id(slides.getId())
                .imageUrl(slides.getImageUrl())
                .text(slides.getText())
                .slideOrder(slides.getSlideOrder())
                .deleted(slides.getDeleted())
                .organizationDto(OrganizationController.toDto(slides.getOrganization()))
                .createdAt(slides.getCreatedAt())
                .updatedAt(slides.getUpdatedAt())
                .build();
    }


    public Slides toDomain(SlidesDto slidesDto){
        return Slides.builder()
                .id(slidesDto.getId())
                .imageUrl(slidesDto.getImageUrl())
                .text(slidesDto.getText())
                .slideOrder(slidesDto.getSlideOrder())
                .organization(OrganizationController.toDomain(slidesDto.getOrganizationDto()))
                .deleted(slidesDto.getDeleted())
                .createdAt(slidesDto.getCreatedAt())
                .updatedAt(slidesDto.getUpdatedAt())
                .build();
    }

    public static SimpleSlideDto toSimpleDto(SimpleSlide slides){
        return SimpleSlideDto.builder()
                .id(slides.getId())
                .text(slides.getText())
                .slideOrder(slides.getSlideOrder())
                .deleted(slides.getDeleted())
                .organizationId(slides.getOrganizationId())
                .createdAt(slides.getCreatedAt())
                .updatedAt(slides.getUpdatedAt())
                .build();
    }

    public SimpleSlide toSimpleDomain(SimpleSlideDto simpleSlideDto){
        return SimpleSlide.builder()
                .id(simpleSlideDto.getId())
                .imageEncoded(simpleSlideDto.getImageEncoded())
                .imageUrl(simpleSlideDto.getImageUrl())
                .text(simpleSlideDto.getText())
                .slideOrder(simpleSlideDto.getSlideOrder())
                .organizationId(simpleSlideDto.getOrganizationId())
                .deleted(simpleSlideDto.getDeleted())
                .createdAt(simpleSlideDto.getCreatedAt())
                .updatedAt(simpleSlideDto.getUpdatedAt())
                .build();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SlidesDto implements Serializable {
        private Long id;
        private String imageUrl;
        @NotBlank
        private String text;
        private Integer slideOrder;
        @NotNull
        private OrganizationDto organizationDto;
        private Boolean deleted;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SimpleSlideDto implements Serializable{
        private Long id;
        private String imageEncoded;
        private String imageUrl;
        @NotBlank
        private String text;
        private Integer slideOrder;
        @NotNull
        private Long organizationId;
        private Boolean deleted;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
