package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entity.OrganizationEntity;
import com.alkemy.ong.data.entity.SlidesEntity;
import com.alkemy.ong.data.repository.OrganizationRepository;
import com.alkemy.ong.data.repository.SlidesRepository;
import com.alkemy.ong.domain.organization.Organization;
import com.alkemy.ong.domain.organization.OrganizationService;
import com.alkemy.ong.domain.slides.SimpleSlide;
import com.alkemy.ong.domain.slides.SlidesGateway;
import com.alkemy.ong.domain.slides.Slides;
import com.alkemy.ong.web.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class DefaultSlidesGateway implements SlidesGateway {
    private final SlidesRepository slidesRepository;
    private final OrganizationRepository organizationRepository;

    public DefaultSlidesGateway(SlidesRepository slidesRepository, OrganizationRepository organizationRepository){

        this.slidesRepository = slidesRepository;
        this.organizationRepository = organizationRepository;
    }

    private Slides toDomain(SlidesEntity slidesEntity){
        return Slides.builder()
                .id(slidesEntity.getId())
                .imageUrl(slidesEntity.getImageUrl())
                .text(slidesEntity.getText())
                .slideOrder(slidesEntity.getSlideOrder())
                .organization(DefaultOrganizationGateway.toModel(slidesEntity.getOrganizationEntity()))
                .deleted(slidesEntity.getDeleted())
                .createdAt(slidesEntity.getCreatedAt())
                .updatedAt(slidesEntity.getUpdatedAt())
                .build();
    }

    private SlidesEntity toEntity(Slides slides){
        return SlidesEntity.builder()
                .id(slides.getId())
                .imageUrl(slides.getImageUrl())
                .text(slides.getText())
                .slideOrder(slides.getSlideOrder())
                .organizationEntity(DefaultOrganizationGateway.toEntity(slides.getOrganization()))
                .build();
    }

    private SlidesEntity simpleToEntity(SimpleSlide slides, OrganizationEntity organization){
        return SlidesEntity.builder()
                .id(slides.getId())
                .imageUrl(slides.getImageUrl())
                .text(slides.getText())
                .slideOrder(slides.getSlideOrder())
                .organizationEntity(organization)
                .build();
    }

    @Override
    public Slides create(SimpleSlide slides) {
        // TODO: almacenar imagenes en Bucket Amazon S3

        OrganizationEntity organization = organizationRepository
                .findById(slides.getOrganizationId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find organization with id: " + slides.getOrganizationId()));

        SlidesEntity slideEntity = simpleToEntity(slides, organization);
        slideEntity.setDeleted(false);

        return toDomain(slidesRepository.save(slideEntity));
    }

    @Override
    public void delete(Long id) {

        SlidesEntity slideToDelete = slidesRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find slide with id: " + id));

        slideToDelete.setDeleted(true);

        slidesRepository.save(slideToDelete);
    }

    @Override
    public List<Slides> findAll() {
        List<SlidesEntity> slidesEntityList = slidesRepository.findAll();

        return slidesEntityList
                .stream()
                .map(slide -> toDomain(slide))
                .collect(toList());
    }

    @Override
    public Slides findById(Long id) {
        SlidesEntity slideToFind = slidesRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find slide with id: " + id));

        return toDomain(slideToFind);
    }
}
