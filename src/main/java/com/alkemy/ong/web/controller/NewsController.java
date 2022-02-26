package com.alkemy.ong.web.controller;


import com.alkemy.ong.domain.news.News;
import com.alkemy.ong.domain.news.NewsService;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;
    
    public NewsController(NewsService newsService){
        this.newsService = newsService;
    }
    
    @PostMapping
    public ResponseEntity<NewsDTO> create(@RequestBody NewsDTO newDTO){
        News news = newsService.create(toDomain(newDTO));
        return ResponseEntity.ok(toDTO(news));
    }

    private News toDomain(NewsDTO newDTO) {
        return News.builder()
                .id(newDTO.getId())
                .name(newDTO.getName())
                .content(newDTO.getContent())
                .image(newDTO.getImage())
                .build();
    }

    private NewsDTO toDTO(News news){
        return NewsDTO.builder()
                .id(news.getId())
                .name(news.getName())
                .content(news.getContent())
                .image(news.getImage())
                .createdAt(news.getCreatedAt())
                .updatedAt(news.getUpdatedAt())
                .build();
    }

    @Data
    @Builder
    private static class NewsDTO{
        private Long id;
        private String name;
        private String content;
        private String image;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
