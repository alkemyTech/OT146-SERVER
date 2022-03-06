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

    @Override
    public List<News> findAll() {
        return newGateway.findAll();
    }

    @Override
    public News update(Long id, News news) {
        return newGateway.update(id, news);
    }

    @Override
    public void deleteById(Long id) {
        newGateway.deleteById(id);

    }

    @Override
    public News findById(Long id) {
        return newGateway.findById(id);
    }
}
