package com.alkemy.ong.domain.slides;

import com.alkemy.ong.domain.storage.Image;
import com.alkemy.ong.domain.storage.StorageGateway;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SlidesService {
    private final SlidesGateway slidesGateways;
    private final StorageGateway storageGateway;

    public SlidesService(SlidesGateway slidesGateways, StorageGateway storageGateway){
        this.slidesGateways = slidesGateways;
        this.storageGateway = storageGateway;
    }

    public Slides create(SimpleSlide slide) {
        String imageUrl = storageGateway
                .save(slide.getImageEncoded(), "slide.jpeg")
                .getUrl();

        slide.setImageUrl(imageUrl);

        return slidesGateways.create(slide);
    }

    public Slides update(Long id, SimpleSlide slide){ return slidesGateways.update(id, slide); }

    public void delete(Long id){ slidesGateways.delete(id); }

    public List<Slides> findAll(){ return slidesGateways.findAll();  }

    public Slides findById(Long id){ return slidesGateways.findById(id); }

}
