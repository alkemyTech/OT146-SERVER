package com.alkemy.ong.domain.news;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class News {
    private Long id;
    private String name;
    private String content;
    private String image;
    private String createdAt;
}
