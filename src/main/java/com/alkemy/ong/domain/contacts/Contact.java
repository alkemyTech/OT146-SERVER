package com.alkemy.ong.domain.contacts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
