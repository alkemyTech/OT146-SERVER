package com.alkemy.ong.web.controller;

import com.alkemy.ong.data.entity.RolesEntity;
import com.alkemy.ong.data.entity.UserEntity;
import com.alkemy.ong.data.repository.UserRepository;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;


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