package com.alkemy.ong.data.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "members")
@Getter
@Setter
@SQLDelete(sql = "UPDATE member SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String facebookUrl;
    private String instagramUrl;
    private String linkedinUrl;

    @Column(nullable = false)
    private String image;
    private String description;

    @Column(name = "creation_date")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate creationDate;

    @Column(name = "modification_date")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate modificationDate;

    private boolean deleted = Boolean.FALSE;
}
