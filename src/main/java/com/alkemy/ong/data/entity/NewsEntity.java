package com.alkemy.ong.data.entity;

import com.alkemy.ong.domain.Category.Category;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String image;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private boolean deleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="categories_id")
    private Category categories;




}
