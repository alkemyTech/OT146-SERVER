package com.alkemy.ong.domain.slides;

import java.util.List;

public interface SlidesGateway {
    Slides create(SimpleSlide slides);
    Slides update(Long id, SimpleSlide slides);
    void delete(Long id);
    List<Slides> findAll();
    Slides findById(Long id);
}
