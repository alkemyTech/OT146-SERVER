package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entity.SlidesEntity;
import com.alkemy.ong.data.repository.SlidesRepository;
import com.alkemy.ong.domain.slides.SlidesGateway;
import com.alkemy.ong.domain.slides.Slides;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;

@Component
public class DefaultSlidesGateway implements SlidesGateway {
    private final SlidesRepository slidesRepository;

    public DefaultSlidesGateway(SlidesRepository slidesRepository){
        this.slidesRepository = slidesRepository;
    }

    public Slides toDomain(SlidesEntity slidesEntity){
        return Slides.builder()
                .id(slidesEntity.getId())
                .imageUrl(slidesEntity.getImageUrl())
                .text(slidesEntity.getText())
                .slideOrder(slidesEntity.getSlideOrder())
                //.organizationId(slidesEntity.getOrganizationId())
                .deleted(slidesEntity.getDeleted())
                .createdAt(slidesEntity.getCreatedAt())
                .updatedAt(slidesEntity.getUpdatedAt())
                .build();
    }

    public SlidesEntity toEntity(Slides slides){
        return SlidesEntity.builder()
                .id(slides.getId())
                .imageUrl(slides.getImageUrl())
                .text(slides.getText())
                .slideOrder(slides.getSlideOrder())
                //.organizationId(slides.getOrganizationId())
                .deleted(slides.getDeleted())
                .createdAt(slides.getCreatedAt())
                .updatedAt(slides.getUpdatedAt())
                .build();
    }


    @Override
    public Slides create(Slides slides) {
        SlidesEntity slideEntity = toEntity(slides);
        return toDomain(slidesRepository.save(slideEntity));
    }

    @Override
    public List<Slides> findAll() {
        List<SlidesEntity> slidesEntityList = (List<SlidesEntity>) slidesRepository.findAll();

        return slidesEntityList
                .stream()
                .map(slide -> toDomain(slide))
                .collect(Collectors.toList());
    }
}
