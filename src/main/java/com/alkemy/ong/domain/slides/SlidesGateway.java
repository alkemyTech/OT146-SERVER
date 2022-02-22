package com.alkemy.ong.domain.slides.gateway;

import com.alkemy.ong.domain.slides.model.Slides;

import java.util.List;

public interface SlidesGateway {
    Slides create(Slides slides);
    List<Slides> findAll();
}
