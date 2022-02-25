package com.alkemy.ong.domain.news;

import java.util.List;

public interface NewsGateway {

    News create (News news);

    List<News> findAll();

}
