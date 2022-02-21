package com.alkemy.ong.web;

import com.alkemy.ong.domain.comments.Commentary;
import com.alkemy.ong.domain.comments.CommentaryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
public class CommentaryController {

    private final CommentaryService commentaryService;

    public CommentaryController(CommentaryService commentaryService) {
        this.commentaryService = commentaryService;
    }

    @GetMapping
    public List<CommentaryDtoResponse> findAll() {
        List<Commentary> models = commentaryService.findAll();
        return models.stream()
                .map(model -> (new CommentaryDtoResponse(model.getBody())))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CommentaryDTO dto){
        Commentary model = new Commentary();
        BeanUtils.copyProperties(dto, model);
        commentaryService.create(model);
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class CommentaryDTO {

    private Long userId;
    private String body;
    private Long newsId;

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class CommentaryDtoResponse {
    private String body;
}

