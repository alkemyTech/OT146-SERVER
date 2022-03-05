package com.alkemy.ong.domain.slides;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SlidesService {
    private final SlidesGateway slidesGateways;

    public SlidesService(SlidesGateway slidesGateways){
        this.slidesGateways = slidesGateways;
    }

    public Slides create(SimpleSlide slide){ return slidesGateways.create(slide); }

    public Slides update(Long id, SimpleSlide slide){ return slidesGateways.update(id, slide); }

    public void delete(Long id){ slidesGateways.delete(id); }

    public List<Slides> findAll(){ return slidesGateways.findAll();  }

    public Slides findById(Long id){ return slidesGateways.findById(id); }

}
