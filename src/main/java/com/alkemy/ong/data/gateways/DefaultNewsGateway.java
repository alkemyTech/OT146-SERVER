package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entity.NewsEntity;
import com.alkemy.ong.data.repository.NewsRepository;
import com.alkemy.ong.domain.news.News;
import com.alkemy.ong.domain.news.NewsGateway;
import com.alkemy.ong.web.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Component
public class DefaultNewsGateway implements NewsGateway {

    private final NewsRepository newsRepository;

    public DefaultNewsGateway(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public News create(News news) {
        return toModel(newsRepository.save(toEntity(news)));
    }

    @Override
    public News findById(Long id) {
        NewsEntity news = newsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("News not found"));
        return toModel(news);
    }

    @Override
    public List<News> findAll() {
        List<NewsEntity> news = newsRepository.findAll();
        return toModelList(news);
    }

    @Override
    public News update(Long id, News news) {
        NewsEntity newsEntity = newsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no news with such ID"));
        newsEntity.setName(news.getName());
        newsEntity.setContent(news.getContent());
        newsEntity.setImage(news.getImage());
        newsEntity.setUpdatedAt(LocalDateTime.now());
        newsRepository.save(newsEntity);
        return toModel(newsEntity);

    }

    @Override
    public void deleteById(Long id) {
     NewsEntity newsEntity   = newsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no news with such ID"));
     newsRepository.delete(newsEntity);

    }


    private NewsEntity toEntity(News news) {
        return NewsEntity.builder()
                .name(news.getName())
                .content(news.getContent())
                .image(news.getImage())
                .build();
    }

    private News toModel(NewsEntity entity) {
        return News.builder()
                .id(entity.getId())
                .name(entity.getName())
                .content(entity.getContent())
                .image(entity.getImage())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private List<News> toModelList(List<NewsEntity> news) {
        return news.stream().map(this::toModel).collect(toList());
    }

}
