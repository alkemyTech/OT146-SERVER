package com.alkemy.ong.domain.slides.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SlideShortResponse {
    private String imageUrl;
    private Integer slideOrder;
}
