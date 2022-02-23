package com.alkemy.ong.domain.organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Organization {

    private long idOrganization;

    private String name;

    private String image;

    private String address;

    private Integer phone;

    private String email;

    private String about_us_text;

    private String welcome_text;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean deleted;

    @Override
    public String toString() {
        return  "Name: " + name + "\n" +
                "Image: " + image + "\n" +
                "Address: " + address + "\n" +
                "Phone: " + phone;
    }

}
