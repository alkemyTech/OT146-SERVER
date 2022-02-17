package com.alkemy.ong.data;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "commentaries")
@Data
public class Commentary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @Column(name = "user_id")
    private User userId;

    private String body;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "news_id")
    private News newsId;

}
