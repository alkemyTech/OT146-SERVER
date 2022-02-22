package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.slides.model.Slides;
import com.alkemy.ong.domain.slides.model.response.SlidesResponse;
import com.alkemy.ong.domain.slides.services.SlidesService;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/slides")
public class SlidesController {
    private final SlidesService slidesService;

    public SlidesController(SlidesService slidesService) {
        this.slidesService = slidesService;
    }

    @GetMapping
    public ResponseEntity<List<SlidesResponse>> getSlides(){
        List<SlidesResponse> returnValue = new ArrayList<>();

        List<SlidesDto> slides = slidesService.findAll()
                .stream()
                .map(slide -> SlidesDto.toDto(slide))
                .collect(Collectors.toList());

        for(SlidesDto slidesDto : slides){
            SlidesResponse slidesModel = new SlidesResponse();
            BeanUtils.copyProperties(slidesDto, slidesModel);
            returnValue.add(slidesModel);
        }

        return new ResponseEntity<>(returnValue, HttpStatus.OK);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SlidesDto implements Serializable {
        @NotEmpty
        private Long id;
        @NotBlank
        private String imageUrl;
        @NotBlank
        private String text;
        @NotEmpty
        private Integer slideOrder;
        //private OrganizationEntity organizationId;
        private Boolean deleted;

        public static SlidesDto toDto(Slides slides){
            return SlidesDto.builder()
                    .id(slides.getId())
                    .imageUrl(slides.getImageUrl())
                    .text(slides.getText())
                    .slideOrder(slides.getSlideOrder())
                    .deleted(slides.getDeleted())
                    //.organizationId(slides.getOrganizationId())
                    .build();
        }
    }
}
