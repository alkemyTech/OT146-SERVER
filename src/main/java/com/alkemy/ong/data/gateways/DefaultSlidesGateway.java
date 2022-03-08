package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entity.OrganizationEntity;
import com.alkemy.ong.data.entity.SlidesEntity;
import com.alkemy.ong.data.repository.OrganizationRepository;
import com.alkemy.ong.data.repository.SlidesRepository;
import com.alkemy.ong.domain.slides.SimpleSlide;
import com.alkemy.ong.domain.slides.SlidesGateway;
import com.alkemy.ong.domain.slides.Slides;
import com.alkemy.ong.domain.storage.CustomMultipartFile;
import com.alkemy.ong.domain.storage.StorageService;
import com.alkemy.ong.web.exceptions.ResourceNotFoundException;
import com.amazonaws.util.Base64;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class DefaultSlidesGateway implements SlidesGateway {
    private final SlidesRepository slidesRepository;
    private final OrganizationRepository organizationRepository;
    private final StorageService storageService;

    public DefaultSlidesGateway(SlidesRepository slidesRepository,
                                OrganizationRepository organizationRepository,
                                StorageService storageService){

        this.slidesRepository = slidesRepository;
        this.organizationRepository = organizationRepository;
        this.storageService = storageService;
    }

    public static Slides toDomain(SlidesEntity slidesEntity){
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

    public static SimpleSlide simpleToDomain(SlidesEntity slidesEntity){
        return SimpleSlide.builder()
                .id(slidesEntity.getId())
                .imageUrl(slidesEntity.getImageUrl())
                .text(slidesEntity.getText())
                .organizationId(slidesEntity.getOrganizationEntity().getIdOrganization())
                .slideOrder(slidesEntity.getSlideOrder())
                .deleted(slidesEntity.getDeleted())
                .createdAt(slidesEntity.getCreatedAt())
                .updatedAt(slidesEntity.getUpdatedAt())
                .build();
    }

    public static SlidesEntity toEntity(Slides slides){
        return SlidesEntity.builder()
                .id(slides.getId())
                .imageUrl(slides.getImageUrl())
                .text(slides.getText())
                .slideOrder(slides.getSlideOrder())
                .organizationEntity(DefaultOrganizationGateway.toEntity(slides.getOrganization()))
                .build();
    }

    public static SlidesEntity simpleToEntity(SimpleSlide slides, OrganizationEntity organization){
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

        OrganizationEntity organization = organizationRepository
                .findById(slides.getOrganizationId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find organization with id: " + slides.getOrganizationId()));

        // --- AMAZON AWS Subida de imagen

        // Quito "data:image/jpeg;base64" para quedarme solo con los bytes a decodear
        String trimmedEncodedImage = slides.getImageEncoded().substring(slides.getImageEncoded().indexOf(",") + 1);

        byte[] decodedBytes = Base64.decode(trimmedEncodedImage);

        // Nombre del archivo con su correspondiente extensión
        String fileName = "slide.jpeg";
        CustomMultipartFile customMultipartFile = new CustomMultipartFile(decodedBytes, fileName);

        try {
            customMultipartFile.transferTo(customMultipartFile.getFile());
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException : " + e);
        } catch (IOException e) {
            System.out.println("IOException : " + e);
        }

        // Almaceno la imagen en AWS y obtengo su correspondiente URL
        String imageUrl = storageService.save(customMultipartFile).getUrl();
        slides.setImageUrl(imageUrl);

        SlidesEntity slideEntity = simpleToEntity(slides, organization);

        // Si no se especifica el orden del slide, busco el último valor y le sumo uno
        if(slideEntity.getSlideOrder() == null){
            slideEntity.setSlideOrder(
                    slidesRepository
                            .findTopByOrderBySlideOrderDesc()
                            .getSlideOrder() + 1
            );
        }

        slideEntity.setDeleted(false);

        return toDomain(slidesRepository.save(slideEntity));
    }

    @Override
    public Slides update(Long id, SimpleSlide slides) {

        SlidesEntity slide = slidesRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find slide with id: " + id));

        slide.setSlideOrder(slides.getSlideOrder());
        slide.setImageUrl(slides.getImageUrl());
        slide.setText(slides.getText());

        return toDomain(slidesRepository.save(slide));
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
