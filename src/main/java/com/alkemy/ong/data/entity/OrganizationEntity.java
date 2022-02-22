package com.alkemy.ong.data.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Entity
@Data
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
    private String aboutUsText;

    @Column(nullable = false)
    private String welcomeText;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(nullable = false)
    private LocalDateTime updatedAt;


    @Column(nullable = false)
    private Boolean deleted;

    @Override
    public String toString() {
        return  "Name: " + name + "\n" +
                "Image: " + image + "\n" +
                "Address: " + address + "\n" +
                "Phone: " + phone;
    }
}
