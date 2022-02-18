package com.alkemy.ong.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Setter
@Getter
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String image;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "modification_date")
    private LocalDateTime modificationDate;

    private boolean deleted;

/*  @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="categories_id")
    private Category categories;*/




}