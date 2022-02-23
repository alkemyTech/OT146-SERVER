package com.alkemy.ong.domain.news;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class newdto {
    private Long id;
    private String name;
    private String content;
    private String image;
}
