package com.alkemy.ong.domain.slides.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SlidesResponse {
    private String imageUrl;
    private Integer slideOrder;
}
