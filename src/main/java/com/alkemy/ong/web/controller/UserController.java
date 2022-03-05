package com.alkemy.ong.web.controller;


import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserService;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
