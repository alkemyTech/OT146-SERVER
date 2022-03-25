package com.alkemy.ong.web.security;

import com.alkemy.ong.data.entity.ContactEntity;
import com.alkemy.ong.data.entity.RolesEntity;
import com.alkemy.ong.data.entity.UserEntity;
import com.alkemy.ong.data.repository.RolesRepository;
import com.alkemy.ong.data.repository.UserRepository;
import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.domain.users.UserService;
import com.alkemy.ong.web.controller.UserController;
import com.alkemy.ong.web.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.config.http.MatcherType.mvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RolesRepository roleRepository;

    @Test
    public void loginAfterRegistration() throws Exception {
        RolesEntity rolesEntity = generateRoleEntity(1L, "Admin", "Admin");

        UserController.UserDTO userDTO = generateUserDto(1L,"Test", "Register", "new@mail.com", "password", 1L);
        UserEntity userEntity = generateUserEntity(1L, "Test", "Register", "new@mail.com", "$2a$10$R4QXAeROWWBkBsxO9UUxoeUV3HsdP2AmAom.iHqYjiQyEWo6X66a2", null, rolesEntity);

        when(roleRepository.save(rolesEntity)).thenReturn(rolesEntity);
        when(roleRepository.findById(userDTO.getRoleId())).thenReturn(Optional.of(rolesEntity));

        when(userRepository.save(toEntity(toDomain(userDTO)))).thenReturn(userEntity);
        when(userRepository.findByEmail("new@mail.com")).thenReturn(Optional.of(userEntity));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO));

        mockMvc.perform(requestBuilder)
                .andExpect(header().exists("access_token"));
    }

    @Test
    public void successLogin() throws Exception {
        UserLoginDto userLoginDto = generateUserLoginDto("Test", "password");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLoginDto));

        mockMvc.perform(requestBuilder)
                .andExpect(header().exists("access_token"));
    }

    @Test
    public void failLogin() throws Exception {
        UserLoginDto userLoginDto = generateUserLoginDto("Test", "not-the-password");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLoginDto));

        mockMvc.perform(requestBuilder)
                .andExpect(header().doesNotExist("access_token"));
    }

    private UserEntity generateUserEntity(Long id, String name, String lastname, String email, String password, String photo, RolesEntity rolesEntity) {
        return UserEntity.builder()
                .id(id)
                .firstName(name)
                .lastName(lastname)
                .email(email)
                .password(password)
                .photo(photo)
                .role(rolesEntity)
                .createdAt(LocalDateTime.now())
                .deleted(false)
                .build();
    }

    private RolesEntity generateRoleEntity(Long id, String name, String description) {
        return RolesEntity.builder()
                .id(id)
                .name(name)
                .description(description)
                .createdAt(LocalDateTime.now())
                .deleted(false)
                .build();
    }

    private UserController.UserDTO generateUserDto(Long id, String firstName, String lastName, String email, String password, Long roleId){
        return UserController.UserDTO.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .roleId(roleId)
                .build();
    }

    private UserLoginDto generateUserLoginDto(String username, String password){
        return UserLoginDto.builder()
                .username(username)
                .password(password)
                .build();
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

    private UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .photo(user.getPhoto())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .role(roleRepository.findById(user.getRoleId()).orElseThrow(
                        () -> new ResourceNotFoundException("Rol not found")
                ))
                .build();
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLoginDto {
        public String username;
        public String password;
    }
}
