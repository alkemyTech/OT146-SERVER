package com.alkemy.ong.web.security;

import com.alkemy.ong.data.entity.ContactEntity;
import com.alkemy.ong.data.entity.RolesEntity;
import com.alkemy.ong.data.entity.UserEntity;
import com.alkemy.ong.data.repository.RolesRepository;
import com.alkemy.ong.data.repository.UserRepository;
import com.alkemy.ong.domain.users.User;
import com.alkemy.ong.web.controller.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        UserEntity newUser = generateUserEntity(null, "Test", "Register", "new@mail.com", "12345678", null, rolesEntity);
        UserEntity registerUserMock = generateUserEntity(1L, "Test", "Register", "new@mail.com", "12345678", null, rolesEntity);
        //UserController.UserDTO userDTO = generateUserDto(1L,"Test", "Register", "new@mail.com", "12345678", 1L);


        when(userRepository.save(newUser)).thenReturn(registerUserMock);
        when(roleRepository.findById(newUser.getRole().getId())).thenReturn(Optional.of(registerUserMock.getRole()));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser));

        mockMvc.perform(requestBuilder)
                .andExpect(header().exists("access_token"));
    }

    @Test
    public void successLogin(){

    }

    @Test
    public void failLogin(){

    }

    private UserEntity generateUserEntity(Long id, String name, String lastname, String email, String password, String photo, RolesEntity rolesEntity) {
        //RolesEntity role = new RolesEntity();
        //role.setId(roleId);
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

//    private UserController.UserDTO generateUserDto(Long id, String firstName, String lastName, String email, String password, Long roleId){
//        return UserController.UserDTO.builder()
//                .id(id)
//                .firstName(firstName)
//                .lastName(lastName)
//                .email(email)
//                .password(password)
//                .roleId(roleId)
//                .build();
//    }
}
