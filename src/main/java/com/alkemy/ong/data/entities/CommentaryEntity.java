package com.alkemy.ong.data.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "commentaries")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @Column(nullable = false, length = 500)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "news_id")
    private NewsEntity newsEntity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
