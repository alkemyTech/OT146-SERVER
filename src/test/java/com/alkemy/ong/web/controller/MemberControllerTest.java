package com.alkemy.ong.web.controller;

import com.alkemy.ong.data.entity.MemberEntity;
import com.alkemy.ong.data.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.alkemy.ong.web.controller.MemberController.*;
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

    @Test
    void save_success() throws Exception {
        MemberDTO memberDTO = buildDto();
        MemberEntity entityWithoutId = buildEntityWithoutId();
        MemberEntity resultEntityWhitId = buildEntityWhitId(1L);

        when(memberRepository.save(entityWithoutId)).thenReturn(resultEntityWhitId);

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDTO)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("TestName")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.facebookUrl", is("https://www.facebook.com/profile")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instagramUrl", is("https://www.instagram.com/profile")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image", is("user/img/photo.jpg")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", is("Member description")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt", is("2022-03-05")));
    }

    @Test
    void getAllMembers_success() throws Exception {
        List<MemberEntity> members = Arrays.asList(buildEntityWhitId(1L), buildEntityWhitId(2L), buildEntityWhitId(3L));

        when(memberRepository.findAll()).thenReturn(members);

        mockMvc.perform(get("/members/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", is(3)));
    }

    @Test
    void getMembersByPage_success() throws Exception {
        List<MemberDTO> memberDTOS = new ArrayList<>();
        Pageable pageable = PageRequest.of(1, 10);
        List<MemberEntity> memberEntities = buildEntityListWhitId();
        Page<MemberEntity> memberEntityPage = new PageImpl(memberEntities);

        when(memberRepository.findByDeleted(false, pageable)).thenReturn(memberEntityPage);

        mockMvc.perform(get("/members?page=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDTOS)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("content").isArray());
    }

    @Test
    void update() throws Exception {
        MemberDTO dtoBody = MemberDTO.builder()
                .name("Update member name")
                .facebookUrl("https://www.facebook.com/profile")
                .instagramUrl("https://www.instagram.com/profile")
                .linkedinUrl("https://www.linkedin.com/profile")
                .image("user/img/photo.jpg")
                .description("Update description")
                .createdAt(LocalDate.of(2022, 03, 05))
                .build();
        MemberEntity resultEntity = buildEntityWhitId(5L);

        when(memberRepository.findById(5L)).thenReturn(Optional.of(resultEntity));
        when(memberRepository.save(resultEntity)).thenReturn(resultEntity);

       mockMvc.perform(put("/members/{id}", 5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoBody)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Update member name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.facebookUrl", is("https://www.facebook.com/profile")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instagramUrl", is("https://www.instagram.com/profile")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image", is("user/img/photo.jpg")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", is("Update description")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt", is("2022-03-05")));
    }

    @Test
    void delete_success() throws Exception {
        MemberEntity entity = buildEntityWhitId(20L);

        when(memberRepository.findById(entity.getId())).thenReturn(Optional.of(entity));

        mockMvc.perform(MockMvcRequestBuilders.delete("/members/{id}", entity.getId()))
                .andExpect(status().isNoContent());
    }


    private MemberEntity buildEntityWhitId(long id) {
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

    private MemberEntity buildEntityWithoutId() {
        return MemberEntity.builder()
                .name("TestName")
                .facebookUrl("https://www.facebook.com/profile")
                .instagramUrl("https://www.instagram.com/profile")
                .linkedinUrl("https://www.linkedin.com/profile")
                .image("user/img/photo.jpg")
                .description("Member description")
                .createdAt(LocalDate.of(2022, 03, 05))
                .build();
    }

    private MemberDTO buildDto() {
        return MemberDTO.builder()
                .name("TestName")
                .facebookUrl("https://www.facebook.com/profile")
                .instagramUrl("https://www.instagram.com/profile")
                .linkedinUrl("https://www.linkedin.com/profile")
                .image("user/img/photo.jpg")
                .description("Member description")
                .createdAt(LocalDate.of(2022, 03, 05))
                .build();
    }

    private List<MemberEntity> buildEntityListWhitId() {
        List<MemberEntity> memberEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            memberEntities.add(buildEntityWhitId(i + 1));
        }
        return memberEntities;
    }

}