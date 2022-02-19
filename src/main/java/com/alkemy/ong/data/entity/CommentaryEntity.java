package com.alkemy.ong.data.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "commentaries")
@Data
public class CommentaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @Column(nullable = false, length = 500)
    private String body;

/*
TODO:
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "news_id")
    private News newsId;
 */

}
