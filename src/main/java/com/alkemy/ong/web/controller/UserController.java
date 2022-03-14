package com.alkemy.ong.web.controller;


import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserService;
import com.alkemy.ong.web.exceptions.BadRequestException;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final PasswordEncoder encoder;

    public UserController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> findUsers(@RequestParam(name = "deleted", required = false) Boolean isDeleted) {
        List<User> users = isDeleted == null ? userService.findAll() : userService.findByDeleted(isDeleted);
        return ResponseEntity.ok(toListDto(users));
    }


    @PostMapping("/auth/register")
    public ResponseEntity<UserDTO> register (@Valid @RequestBody UserDTO newUser){

        if(userService.existsByEmail(newUser.email)){
            throw new BadRequestException("The email is already registered");
        }


        newUser.setPassword(encoder.encode(newUser.getPassword()));
        User user = userService.save(toDomain(newUser));

        return new ResponseEntity<UserDTO>(toDto(user), HttpStatus.CREATED);
    }


    @Data
    private static class UserDTO {
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
        private String email;

        @NotBlank(message="The password can´t be empty")
        @Size(min = 6, max = 255, message = "Password length must be between 6 and 255 characters")
        @Column(nullable = false)
        private String password;

        @Size(max = 255, message = "The maximum photo length should be 255 characters")
        @Column
        private String photo;

        private Long roleId;

        private String roleName;

        @Column(name = "created_at")
        @CreationTimestamp
        private LocalDateTime createdAt;

        @Column(name = "updated_at")
        @UpdateTimestamp
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

    private User toDomain(UserController.UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .photo(dto.getPhoto())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .roleId(dto.getRoleId())
                .build();
    }

    private List<UserDTO> toListDto(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .collect(toList());
    }

}
