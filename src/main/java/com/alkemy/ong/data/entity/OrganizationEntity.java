package com.alkemy.ong.data.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

@Entity
@Data
@Table(name = "organizations")
public class OrganizationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idOrganization;

    @NotBlank(message="The name can´t be empty")
    @Size(min = 3, max = 45, message = "Name length must be between 3 and 45 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message="The image can´t be empty")
    @Size(min = 10, max = 256, message = "Image length must be between 10 and 256 characters")
    @Column(nullable = false)
    private String image;

    @Size(max = 45, message = "The maximum image length should be 45 characters")
    @Column
    private String address;

    //@DecimalMax(9999999999999)
    @Column
    private Integer phone;

    @NotBlank(message="The email can´t be empty")
    @Size(min = 10, max = 45, message = "Email length must be between 10 and 45 characters")
    @Email
    @Column(nullable = false)
    private String email;

    @Size(max = 65535, message = "The maximum 'About us' text length should be 65535 characters")
    @Column
    private String about;

    @NotBlank(message="The 'Welcome' text can´t be empty")
    @Size(min = 20, max = 65535, message = "'Welcome' text length must be between 20 and 65535 characters")
    @Column(nullable = false)
    private String welcome;

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
