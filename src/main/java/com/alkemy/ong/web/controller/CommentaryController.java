package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.comments.Commentary;
import com.alkemy.ong.domain.comments.CommentaryService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/comments")
public class CommentaryController {

    private final CommentaryService commentaryService;

    public CommentaryController(CommentaryService commentaryService) {
        this.commentaryService = commentaryService;
    }

    @GetMapping
    public List<CommentaryBodyDto> findAll() {
        List<Commentary> commentaries = commentaryService.findAll();
        return commentaries.stream()
                .map(model -> (new CommentaryBodyDto(model.getBody())))
                .collect(toList());
    }

    @GetMapping("/details")
    public List<CommentaryDTO> findAllWithDetails() {
        List<Commentary> commentaries = commentaryService.findAll();
        return commentaries.stream()
                .map(this::toDto)
                .collect(toList());
    }

    @PostMapping()
    public ResponseEntity<CommentaryDTO> create(@Valid @RequestBody CommentaryDTO dto) {
        Commentary commentary = commentaryService.create(toDomain(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(commentary));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class CommentaryDTO {
        @NotNull
        private Long userId;
        @NotBlank
        private String body;
        @NotNull
        private Long newsId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class CommentaryBodyDto {
        private String body;
    }


    private CommentaryDTO toDto(Commentary domain) {
        return CommentaryDTO.builder()
                .userId(domain.getUserId())
                .body(domain.getBody())
                .newsId(domain.getNewsId())
                .build();
    }

    private Commentary toDomain(CommentaryDTO dto) {
        return Commentary.builder()
                .userId(dto.getUserId())
                .body(dto.getBody())
                .newsId(dto.getNewsId())
                .build();
    }

}





