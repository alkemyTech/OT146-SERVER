package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserService;
import com.alkemy.ong.web.exceptions.BadRequestException;
import com.alkemy.ong.web.security.jwt.CustomAuthenticationFilter;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import static com.alkemy.ong.web.security.jwt.JwtUtils.generateAccessToken;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;


    public UserController(UserService userService, PasswordEncoder encoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findUsers(@RequestParam(name = "deleted", required = false) Boolean isDeleted) {
        List<User> users = isDeleted == null ? userService.findAll() : userService.findByDeleted(isDeleted);
        return ResponseEntity.ok(toListDto(users));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO newUser, HttpServletResponse response, HttpServletRequest request) throws ServletException {


        if (userService.existsByEmail(newUser.email)) {
            throw new BadRequestException("The email is already registered");
        }

        String plainPass = newUser.getPassword();

        newUser.setPassword(encoder.encode(newUser.getPassword()));
        User user = userService.save(toDomain(newUser));

        CustomAuthenticationFilter customAuth = new CustomAuthenticationFilter(authenticationManager);
        Authentication authentication = customAuth.attemptAuthentication(user.getEmail(), plainPass);
        org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        String access_token = generateAccessToken(springUser, request);
        response.setHeader("access_token", access_token);

        return new ResponseEntity<UserDTO>(toDto(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> show(@PathVariable Long id) {
        UserDTO userDto = toDto(userService.findById(id));

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedUserMail =  ((String) principal);

        if (userDto.getEmail().equals(loggedUserMail)) {
            return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<UserDTO>(userDto, HttpStatus.FORBIDDEN);
        }      
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable("id") Long id,
                                          @Valid @RequestBody UserDTO dto) {

        dto.setPassword(encoder.encode(dto.password));
        UserDTO dtoUpdated = toDto(userService.update(id, toDomain(dto)));
        return new ResponseEntity<>(dtoUpdated, HttpStatus.OK);
    }


    @Data
    private static class UserDTO {
        private Long id;

        @NotBlank(message = "The first name can´t be empty")
        @Size(min = 3, max = 255, message = "First name length must be between 3 and 255 characters")
        @Column(nullable = false)
        private String firstName;

        @NotBlank(message = "The last name can´t be empty")
        @Size(min = 3, max = 255, message = "Last name length must be between 3 and 255 characters")
        @Column(nullable = false)
        private String lastName;

        @NotBlank(message = "The email can´t be empty")
        @Size(min = 10, max = 255, message = "Email length must be between 10 and 255 characters")
        @Email
        private String email;

        @NotBlank(message="The password can´t be empty")
        @Size(min = 6, max = 255, message = "Password length must be between 6 and 255 characters")

        @NotBlank(message = "The password can´t be empty")
        @Size(min = 8, max = 255, message = "Password length must be between 8 and 255 characters")
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
