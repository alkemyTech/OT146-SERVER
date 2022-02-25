package com.alkemy.ong.web.controller;


import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserService;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
//TODO: @PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> users = userService.findAll();
        List<UserDTO> dtos = users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/actives")
    public ResponseEntity<List<UserDTO>> showActives() {
        List<User> actives = userService.showActives();
        List<UserDTO> dtos = actives.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<UserDTO>> showDeleted() {
        List<User> deleted = userService.showDeleted();
        List<UserDTO> dtos = deleted.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
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

}
