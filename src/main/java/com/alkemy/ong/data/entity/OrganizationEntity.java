package com.alkemy.ong.data.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "organizations")
public class OrganizationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idOrganization;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @Column
    private String address;

    //@DecimalMax(9999999999999)
    @Column
    private Integer phone;

    @Column(nullable = false)
    private String email;

    @Column
    private String about_us_text;

    @Column(nullable = false)
    private String welcome_text;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(nullable = false)
    private LocalDateTime updatedAt;


    @Column(nullable = false)
    private Boolean deleted;

    @Column
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organizationEntity", orphanRemoval = true)
    @OrderBy("slideOrder")
    private List<SlidesEntity> slides = new ArrayList<>();

    @Column
    private String facebookLink;

    @Column
    private String instagramLink;

    @Column
    private String linkedinLink;

    @Override
    public String toString() {
        return  "Name: " + name + "\n" +
                "Image: " + image + "\n" +
                "Address: " + address + "\n" +
                "Phone: " + phone;
    }
}
