package com.alkemy.ong.domain.slides;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Slides {
    private Long id;
    private String imageUrl;
    private String text;
    private Integer slideOrder;
    //private OrganizationEntity organizationId;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
