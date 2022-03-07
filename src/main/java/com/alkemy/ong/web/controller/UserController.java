package com.alkemy.ong.web.controller;


import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserService;
import com.alkemy.ong.web.exceptions.BadRequestException;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findUsers(@RequestParam(name = "deleted", required = false) Boolean isDeleted) {
        List<User> users = isDeleted == null ? userService.findAll() : userService.findByDeleted(isDeleted);
        return ResponseEntity.ok(toListDto(users));
    }

    @Data
    private static class UserDTO {
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private Long id;

        @NotBlank(message="The first name can´t be empty")
        @Size(min = 3, max = 255, message = "First name length must be between 3 and 255 characters")
        @Column(nullable = false)
        private String firstName;

        @NotBlank(message="The last name can´t be empty")
        @Size(min = 3, max = 255, message = "Last name length must be between 3 and 255 characters")
        @Column(nullable = false)
        private String lastName;

        @NotBlank(message="The email can´t be empty")
        @Size(min = 10, max = 255, message = "Email length must be between 10 and 255 characters")
        @Email
        @Column(nullable = false, unique = true)
        private String email;

        @NotBlank(message="The password can´t be empty")
        @Size(min = 8, max = 255, message = "Password length must be between 8 and 255 characters")
        @Column(nullable = false)
        private String password;

        @Size(max = 255, message = "The maximum photo length should be 255 characters")
        @Column
        private String photo;

        private Long roleId;

        private String roleName;

        @Column(name = "created_at")
        @CreatedDate
        private LocalDateTime createdAt;

        @Column(name = "updated_at")
        @LastModifiedDate
        private LocalDateTime updatedAt;
    }

    private UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        if (dto.getPhoto() == null || dto.getPhoto().isEmpty()) {
            dto.setPhoto("-----");
        }
        return dto;
    }


    private List<UserDTO> toListDto(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .collect(toList());
    }

}
