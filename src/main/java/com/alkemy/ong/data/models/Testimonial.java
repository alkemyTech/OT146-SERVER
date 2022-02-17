package com.alkemy.ong.data.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "testimonials")
@Getter @Setter
public class Testimonial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "image")
    private String image;
    @Column(name = "content")
    private String content;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "modification_date")
    private LocalDateTime modificationDate;
    @Column(name = "is_deleted")
    @Type(type = "numeric_boolean")
    private boolean is_deleted = Boolean.FALSE;
}
