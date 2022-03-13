package com.alkemy.ong.domain.slides;

import com.alkemy.ong.domain.cloud.CloudGateway;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SlidesService {
    private final SlidesGateway slidesGateways;
    private final CloudGateway cloudGateway;

    public SlidesService(SlidesGateway slidesGateways, CloudGateway cloudGateway){
        this.slidesGateways = slidesGateways;
        this.cloudGateway = cloudGateway;
    }

    public Slides create(SimpleSlide slide) {
        String imageUrl = cloudGateway
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
