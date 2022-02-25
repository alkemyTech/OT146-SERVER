package com.alkemy.ong.domain.news;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService implements NewsGateway {

    private final NewsGateway newGateway;

    public NewsService(NewsGateway newGateway) {
        this.newGateway = newGateway;
    }

    @Override
    public News create(News news) {
        return newGateway.create(news);
    }

//    @Override
//    public List<News> findAll() {
//        return newGateway.findAll();
//    }

}
