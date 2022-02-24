package com.alkemy.ong.domain.slides;

import java.util.List;

public interface SlidesGateway {
    void delete(Long id);
    List<Slides> findAll();
}
