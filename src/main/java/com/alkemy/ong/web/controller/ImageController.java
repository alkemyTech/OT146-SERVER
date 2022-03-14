package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.cloud.Image;
import com.alkemy.ong.domain.cloud.CloudService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/image")
public class ImageController {
    private final CloudService cloudService;

    public ImageController(CloudService cloudService) {
        this.cloudService = cloudService;
    }

    @PostMapping()
    public ResponseEntity<FileInfoResponse> save(@RequestParam("file") MultipartFile multipartFile){
        Image image = cloudService.save(multipartFile);
        return ResponseEntity.ok().body(toFileInfoResponse(image));
    }

    private FileInfoResponse toFileInfoResponse(Image image){
        return FileInfoResponse.builder()
                .name(image.getFullName())
                .type(image.getFullName().substring(image.getFullName().lastIndexOf(".")).replace(".", ""))
                .url(image.getUrl())
                .build();
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    public static class FileInfoResponse {
        private String name;
        private String type;
        private String url;
    }
}
