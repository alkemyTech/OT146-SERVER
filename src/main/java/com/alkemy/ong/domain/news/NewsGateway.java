package com.alkemy.ong.domain.news;

import java.util.List;

public interface NewsGateway {

    News create (News news);
    News findById(Long id);
    List<News> findAll();
    News update(Long id, News news);
}
