package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.slides.Slides;
import com.alkemy.ong.domain.slides.SlidesService;
import com.alkemy.ong.domain.slides.response.SlidesResponse;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.alkemy.ong.web.controller.SlidesController.SlidesDto.toDto;
import static java.util.stream.Collectors.toList;

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
                .map(slide -> toDto(slide))
                .collect(toList());

        for(SlidesDto slidesDto : slides){
            SlidesResponse slidesModel = new SlidesResponse();
            BeanUtils.copyProperties(slidesDto, slidesModel);
            returnValue.add(slidesModel);
        }

        return new ResponseEntity<>(returnValue, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteSlide(@PathVariable Long id){
        slidesService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
