package com.alkemy.ong.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@Table(name = "organizations")
public class OrganizationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idOrganization;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotEmpty
    @Column(nullable = false)
    private String image;

    @Column
    private String address;

    @Column
    private Integer phone;

    @NotEmpty
    @Column(nullable = false)
    private String email;

    @NotEmpty
    @Column(nullable = false)
    private String welcomeText;

    @Column
    private String aboutUsText;

    @NotEmpty
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @NotEmpty
    @Column(nullable = false)
    private LocalDateTime deletedAt;

    @Column
    private Boolean isDelete;

}
