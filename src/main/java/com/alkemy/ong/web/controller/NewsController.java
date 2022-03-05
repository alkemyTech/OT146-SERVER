package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.news.News;
import com.alkemy.ong.domain.news.NewsService;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping
    public ResponseEntity<NewsDTO> create(@RequestBody NewsDTO newDTO) {
        News news = newsService.create(toDomain(newDTO));
        return ResponseEntity.ok(toDTO(news));
    }

    @GetMapping
    public ResponseEntity<List<NewsDTO>> getNews() {
        List<News> newsList = newsService.findAll();
        List<NewsDTO> dtoList = toDtoList(newsList);
        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> showId(@PathVariable Long id) {
        News news = newsService.findById(id);
        NewsDTO newsDTO = toDTO(news);
        return ResponseEntity.ok().body(newsDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsDTO> updateNews(@PathVariable Long id, @RequestBody @Valid NewsDTO newsDto) {
        News news = newsService.findById(id);
        news.setName(newsDto.getName());
        news.setImage(newsDto.getImage());
        news.setContent(newsDto.getContent());
        news = newsService.update(id, news);
        news.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.ok().body(toDTO(news));


    }

    private News toDomain(NewsDTO newDTO) {
        return News.builder()
                .id(newDTO.getId())
                .name(newDTO.getName())
                .content(newDTO.getContent())
                .image(newDTO.getImage())
                .build();
    }

    private NewsDTO toDTO(News news) {
        return NewsDTO.builder()
                .id(news.getId())
                .name(news.getName())
                .content(news.getContent())
                .image(news.getImage())
                .createdAt(news.getCreatedAt())
                .updatedAt(news.getUpdatedAt())
                .build();
    }

    private List<NewsDTO> toDtoList(List<News> news) {
        return news.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Data
    @Builder
    private static class NewsDTO {
        private Long id;
        private String name;
        private String content;
        private String image;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
