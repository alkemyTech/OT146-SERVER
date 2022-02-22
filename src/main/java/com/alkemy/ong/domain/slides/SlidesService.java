package com.alkemy.ong.domain.slides;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SlidesService {
    private final SlidesGateway slidesGateways;

    public SlidesService(SlidesGateway slidesGateways){
        this.slidesGateways = slidesGateways;
    }

    public Slides create(Slides slide){
        return slidesGateways.create(slide);
    }

    public List<Slides> findAll(){ return slidesGateways.findAll();  }
}
