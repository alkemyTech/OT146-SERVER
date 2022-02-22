package com.alkemy.ong.web;

import com.alkemy.ong.domain.comments.Commentary;
import com.alkemy.ong.domain.comments.CommentaryService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

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
                .map(toDto())
                .collect(toList());
    }

    @PostMapping()
    public ResponseEntity<CommentaryDTO> create(@RequestBody CommentaryDTO dto) {
        Commentary commentary = new Commentary();
        BeanUtils.copyProperties(dto, commentary);
        Commentary commentaryDomain = commentaryService.create(commentary);
        CommentaryDTO commentaryDTO = getCommentaryDTO(commentaryDomain);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentaryDTO);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class CommentaryDTO {
        private Long userId;
        private String body;
        private Long newsId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class CommentaryBodyDto {
        private String body;
    }

    private Function<Commentary, CommentaryDTO> toDto() {
        return model -> (new CommentaryDTO(model.getUserId(), model.getBody(), model.getNewsId()));
    }

    private CommentaryDTO getCommentaryDTO(Commentary commentaryDomain) {
        return CommentaryDTO.builder()
                .userId(commentaryDomain.getUserId())
                .body(commentaryDomain.getBody())
                .newsId(commentaryDomain.getNewsId())
                .build();
    }
    
}





