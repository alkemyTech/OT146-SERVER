package com.alkemy.ong.data.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@SQLDelete(sql = "UPDATE news  SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Data
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
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean deleted;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="categories_id")
//    private Category categories;

    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }

        final NewsEntity other = (NewsEntity) obj;
        if (other.getId() != null){
            return other.getId().equals(getId()) &&
                    other.getName().equals(getName()) &&
                    other.getContent().equals(getContent()) &&
                    other.getImage().equals(getImage()) &&
                    other.getCreatedAt().equals(getCreatedAt());
        } else {
            return other.getId().equals(getId()) &&
                    other.getName().equals(getName()) &&
                    other.getContent().equals(getContent()) &&
                    other.getImage().equals(getImage()) &&
                    other.getCreatedAt().equals(getCreatedAt());
        }
    }
}
