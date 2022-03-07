package com.alkemy.ong.web.controller;



import com.alkemy.ong.domain.comments.Commentary;

import com.alkemy.ong.domain.news.News;
import com.alkemy.ong.domain.news.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/news")
@Api(value = "news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }


    @ApiOperation(value = "Create News")
    @PostMapping
    public ResponseEntity<NewsDTO> create(@RequestBody NewsDTO newDTO) {
        News news = newsService.create(toDomain(newDTO));
        return ResponseEntity.ok(toDTO(news));
    }

    @ApiOperation(value = "List News")
    @GetMapping
    public ResponseEntity<List<NewsDTO>> getNews() {
        List<News> newsList = newsService.findAll();
        List<NewsDTO> dtoList = toDtoList(newsList);
        return ResponseEntity.ok().body(dtoList);
    }

    @ApiOperation(value = "Get News by Id")
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

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNews(@PathVariable Long id) {
        News news = newsService.findById(id);
        newsService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

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
    @ApiModel
    private static class NewsDTO {
        @ApiModelProperty(name = "id")
        private Long id;

        @ApiModelProperty(name = "name",
                required = true,
                value = "name of the new")
        @NotBlank
        private String name;

        @ApiModelProperty(name = "content",
                required = true,
                value = "content of the new")
        @NotBlank
        private String content;

        @ApiModelProperty(name = "image",
                value = "Image url")
        private String image;

        @ApiModelProperty(name = "createdAt")
        private LocalDateTime createdAt;

        @ApiModelProperty(name = "updatedAt")
        private LocalDateTime updatedAt;
        private List<Commentary> comments;
    }
}
