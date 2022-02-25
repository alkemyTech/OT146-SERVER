package com.alkemy.ong.domain.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String photo;

    private Long roleId;

    private String roleName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
