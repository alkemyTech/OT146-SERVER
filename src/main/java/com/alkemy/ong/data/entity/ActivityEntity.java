package com.alkemy.ong.data.entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.*;
import java.time.LocalDateTime;
@Builder
@Getter
@Setter
@Entity
@Table(name = "activities")
@SQLDelete(sql = "UPDATE activities SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class ActivityEntity  {

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
        @CreatedDate
        private LocalDateTime createdAt;

        @Column(name = "updated_at")
        @LastModifiedDate
        private LocalDateTime updatedAt;

        private boolean deleted = Boolean.FALSE;



}
