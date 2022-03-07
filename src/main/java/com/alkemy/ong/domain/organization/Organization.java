package com.alkemy.ong.domain.organization;

import com.alkemy.ong.data.entity.SlidesEntity;
import com.alkemy.ong.domain.slides.SimpleSlide;
import com.alkemy.ong.domain.slides.Slides;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
