package com.alkemy.ong.domain.activities;

import lombok.*;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Activity {
    private Long id;
    private String name;
    private String content;
    private String image;
}

