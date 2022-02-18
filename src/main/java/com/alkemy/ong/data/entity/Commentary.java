package com.alkemy.ong.data.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "commentaries")
@Data
public class Commentary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

/*
TODO:
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @Column(name = "user_id")
    private User userId;
 */

    private String body;

/*
TODO:
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "news_id")
    private News newsId;
 */

}
