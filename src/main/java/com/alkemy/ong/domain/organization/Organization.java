package com.alkemy.ong.domain.organization;

import com.alkemy.ong.domain.slides.SimpleSlide;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private String facebookLink;

    private String instagramLink;

    private String linkedinLink;

    private List<SimpleSlide> slides = new ArrayList<>();

    @Override
    public String toString() {
        return  "Name: " + name + "\n" +
                "Image: " + image + "\n" +
                "Address: " + address + "\n" +
                "Phone: " + phone;
    }

}
