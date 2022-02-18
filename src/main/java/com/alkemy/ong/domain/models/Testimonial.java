package com.alkemy.ong.domain.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class Testimonial {
    private Long id;
    private String name;
    private String image;
    private String content;
}
