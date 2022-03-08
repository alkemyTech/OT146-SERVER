package com.alkemy.ong.web.controller;

import com.alkemy.ong.data.entity.MemberEntity;
import com.alkemy.ong.data.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberRepository memberRepository;

    @Autowired
    ObjectMapper objectMapper;

    private MemberEntity buildMember(long id) {
        return MemberEntity.builder()
                .id(id)
                .name("TestName")
                .facebookUrl("https://www.facebook.com/profile")
                .instagramUrl("https://www.instagram.com/profile")
                .linkedinUrl("https://www.linkedin.com/profile")
                .image("user/img/photo.jpg")
                .description("Member description")
                .createdAt(LocalDate.of(2022, 03, 05))
                .build();
    }

    @Test
    void save() throws Exception {
        MemberEntity entityNoId = new MemberEntity();
        entityNoId.setId(1L);
        entityNoId.setName("TestName");
        entityNoId.setFacebookUrl("https://www.facebook.com/profile");
        entityNoId.setInstagramUrl("https://www.instagram.com/profile");
        entityNoId.setLinkedinUrl("https://www.linkedin.com/profile");
        entityNoId.setImage("user/img/photo.jpg");
        entityNoId.setDescription("description member test");
        entityNoId.setCreatedAt(LocalDate.of(2022, 03, 05));

        MemberEntity resultEntity = buildMember(1L);

        when(memberRepository.save(entityNoId)).thenReturn(resultEntity);

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resultEntity)))
                .andExpect(status().isCreated());
    }

    @Test
    void getMembers() throws Exception {
        List<MemberEntity> members = Arrays.asList(buildMember(1L), buildMember(2L), buildMember(3L));

        when(memberRepository.findAll()).thenReturn(members);

        mockMvc.perform(get("/members/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", is(3)));
    }


    @Test
    void getMembersByPage() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() throws Exception {
        MemberEntity entity = buildMember(20L);

        when(memberRepository.findById(entity.getId())).thenReturn(Optional.of(entity));

        mockMvc.perform(MockMvcRequestBuilders.delete("/members/{id}", entity.getId()))
                .andExpect(status().isNoContent());
    }
}