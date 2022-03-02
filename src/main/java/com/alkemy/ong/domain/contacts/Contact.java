package com.alkemy.ong.domain.contacts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contact {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private String message;
}
