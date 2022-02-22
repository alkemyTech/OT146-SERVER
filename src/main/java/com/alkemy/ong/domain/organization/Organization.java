package com.alkemy.ong.domain.organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
