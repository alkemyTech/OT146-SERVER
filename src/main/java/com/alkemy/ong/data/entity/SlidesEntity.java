package com.alkemy.ong.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "slides")
public class SlidesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @Column
    private String text;

    @Column(nullable = false)
    private Integer order;

    @Column(nullable = false)
    private Long organizationId;
}
