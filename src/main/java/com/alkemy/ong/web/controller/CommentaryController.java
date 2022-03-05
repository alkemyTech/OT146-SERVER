package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.comments.Commentary;
import com.alkemy.ong.domain.comments.CommentaryService;
import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UserService userService;

    public CommentaryController(CommentaryService commentaryService, UserService userService) {
        this.commentaryService = commentaryService;
        this.userService = userService;
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

    @PutMapping("/{id}")
    public ResponseEntity<CommentaryDTO> update(@Valid @RequestBody CommentaryDTO commentaryDTO, @PathVariable long id){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String loggedUserMail = ((UserDetails)principal).getUsername();

        User loggedUser = userService.findByEmail(loggedUserMail);
        User user = userService.findById(commentaryDTO.getUserId());

        if (user.getEmail().equals(loggedUserMail) || loggedUser.getRoleId() == 1) {
            return new ResponseEntity<CommentaryDTO>(toDto(commentaryService.update(toDomain(commentaryDTO))), HttpStatus.CREATED);
        }

        return new ResponseEntity<CommentaryDTO>(toDto(commentaryService.findById(commentaryDTO.getId())), HttpStatus.FORBIDDEN);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class CommentaryDTO {
        @NotNull
        private Long id;
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
                .id(domain.getId())
                .userId(domain.getUserId())
                .body(domain.getBody())
                .newsId(domain.getNewsId())
                .build();
    }

    private Commentary toDomain(CommentaryDTO dto) {
        return Commentary.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .body(dto.getBody())
                .newsId(dto.getNewsId())
                .build();
    }

}





