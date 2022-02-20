package com.alkemy.ong.domain.models;


import lombok.*;

@Getter @Setter @AllArgsConstructor
@NoArgsConstructor
@Builder
public class Testimonial {
    private Long id;
    private String name;
    private String image;
    private String content;
}
