package com.alkemy.ong.data.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Entity
@Table(name = "activities")
@SQLDelete(sql = "UPDATE activities SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Activity {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;


        private String name;
        private String content;
        private String image;


        @Column(name = "created_at")
        @CreatedDate
        private LocalDateTime createdAt;

        @Column(name = "updated_at")
        @LastModifiedDate
        private LocalDateTime updatedAt;

        private boolean deleted = Boolean.FALSE;

    }
