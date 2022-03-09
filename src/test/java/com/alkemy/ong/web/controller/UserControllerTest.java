package com.alkemy.ong.web.controller;

import com.alkemy.ong.data.entity.RolesEntity;
import com.alkemy.ong.data.entity.UserEntity;
import com.alkemy.ong.data.repository.RolesRepository;
import com.alkemy.ong.data.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RolesRepository roleRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @WithMockUser(roles = "ADMIN")
    void testAuthorizeWithRoleAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testAuthorizeWithRoleUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllUsers() throws Exception {
        UserEntity userMock1 = generateUserEntity(111L, "User1", "Test", "user1@mail.com", "1234", "image");
        UserEntity userMock2 = generateUserEntity(555L, "User2", "Test", "user2@mail.com", "1234", null);
        List<UserEntity> userList = List.of(userMock1, userMock2);

        when(userRepository.findAll()).thenReturn(userList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(111)))
                .andExpect(jsonPath("$[1].id", is(555)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllDeletedUsers() throws Exception {
        UserEntity userMock1 = generateUserEntity(111L, "User1", "Test", "user1@mail.com", "1234", "image");
        UserEntity userMock2 = generateUserEntity(555L, "User2", "Test", "user2@mail.com", "1234", null);
        List<UserEntity> deletedUsers = List.of(userMock1, userMock2);

        when(userRepository.findByDeleted(true)).thenReturn(deletedUsers);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users")
                .param("deleted", "true")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(111)))
                .andExpect(jsonPath("$[1].id", is(555)));
    }

    @Test
    void testRegisterUser() throws Exception {
        RolesEntity role = new RolesEntity();
        role.setId(1L);
        UserEntity registerUserMock = generateUserEntity(25L, "Test", "Register", "new@mail.com", "12345678", null);

        when(userRepository.save(any(UserEntity.class))).thenReturn(registerUserMock);
        when(roleRepository.findById(any())).thenReturn(Optional.of(role));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerUserMock));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(25)))
                .andExpect(jsonPath("$.firstName", is("Test")))
                .andExpect(jsonPath("$.lastName", is("Register")))
                .andExpect(jsonPath("$.email", is("new@mail.com")));
    }

    @Test // NO SE COMO HACER PARA VERIFICAR QUE FUNCIONA LA EXCEPCION :(
    void testEmailTaken() throws Exception {
        when(userRepository.existsByEmail(any())).thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/auth/register")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }


    private UserEntity generateUserEntity(Long id, String name, String lastname, String email, String password, String photo) {
        return UserEntity.builder()
                .id(id)
                .firstName(name)
                .lastName(lastname)
                .email(email)
                .password(password)
                .photo(photo)
                .role(new RolesEntity())
                .createdAt(LocalDateTime.now())
                .deleted(false)
                .build();
    }

}