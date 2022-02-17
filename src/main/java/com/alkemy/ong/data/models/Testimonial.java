package com.alkemy.ong.data.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "testimonials")
public class Testimonial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "image")
    private String image;
    @Column(name = "content")
    private String content;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name = "is_deleted")
    @Type(type = "numeric_boolean")
    private boolean is_deleted;
}
