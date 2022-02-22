package com.alkemy.ong.domain.slides;

import java.util.List;

public interface SlidesGateway {
    Slides create(Slides slides);
    List<Slides> findAll();
}
