package com.alkemy.ong.web.controllers;

import com.alkemy.ong.data.entities.MemberEntity;
import com.alkemy.ong.data.repositories.MemberRepository;
import com.alkemy.ong.web.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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
import java.util.List;
import java.util.Optional;

import static com.alkemy.ong.web.controllers.MemberController.*;
import static java.util.Arrays.*;
import static org.hamcrest.Matchers.*;
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
        MemberEntity entityWithoutId = buildEntity(null);
        MemberEntity resultEntityWhitId = buildEntity(1L);

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
    void save_BadRequest() throws Exception {
        MemberDTO memberDTO = buildDto();
        memberDTO.setName(null);
        memberDTO.setImage(null);
        MemberEntity entityWithoutId = buildEntity(null);
        MemberEntity resultEntityWhitId = buildEntity(1L);

        when(memberRepository.save(entityWithoutId)).thenReturn(resultEntityWhitId);

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllMembers_success() throws Exception {
        List<MemberEntity> members = asList(buildEntity(1L), buildEntity(2L), buildEntity(3L));

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
        List<MemberEntity> memberEntities = buildEntityListWithId();
        Page<MemberEntity> memberEntityPage = new PageImpl(memberEntities);

        when(memberRepository.findByDeleted(false, pageable)).thenReturn(memberEntityPage);

        mockMvc.perform(get("/members?page=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDTOS)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("content").isArray());
    }

    @Test
    void getMembersByPage_BadRequest() throws Exception {
        List<MemberDTO> memberDTOS = new ArrayList<>();
        Pageable pageable = PageRequest.of(1, 10);
        List<MemberEntity> memberEntities = buildEntityListWithId();
        Page<MemberEntity> memberEntityPage = new PageImpl(memberEntities);

        when(memberRepository.findByDeleted(false, pageable)).thenReturn(memberEntityPage);

        mockMvc.perform(get("/members?page=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDTOS)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getMembersByPage_NotFound() throws Exception {
        List<MemberDTO> memberDTOS = new ArrayList<>();
        Pageable pageable = PageRequest.of(1, 10);
        List<MemberEntity> memberEntities = buildEntityListWithId();
        Page<MemberEntity> memberEntityPage = new PageImpl(memberEntities);

        when(memberRepository.findByDeleted(false, pageable)).thenReturn(memberEntityPage);

        mockMvc.perform(get("/members/page/00")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDTOS)))
                .andExpect(status().isNotFound());
    }

    @Test
    void update_success() throws Exception {
        MemberDTO dtoBody = buildDto();
        MemberEntity resultEntity = buildEntity(5L);

        when(memberRepository.findById(5L)).thenReturn(Optional.of(resultEntity));
        when(memberRepository.save(resultEntity)).thenReturn(resultEntity);

       mockMvc.perform(put("/members/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoBody)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("TestName")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.facebookUrl", is("https://www.facebook.com/profile")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instagramUrl", is("https://www.instagram.com/profile")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image", is("user/img/photo.jpg")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", is("Member description")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt", is("2022-03-05")));
    }

    @Test
    void update_BadRequest() throws Exception {
        MemberDTO dtoBody = MemberDTO.builder()
                .name(null)
                .image(null)
                .build();
        MemberEntity resultEntity = buildEntity(5L);

        when(memberRepository.findById(5L)).thenReturn(Optional.of(resultEntity));
        when(memberRepository.save(resultEntity)).thenReturn(resultEntity);

        mockMvc.perform(put("/members/{id}", 5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoBody)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_NotFound() throws Exception {
        MemberDTO dtoBody = buildDto();

        when(memberRepository.findById(8L)).thenThrow(new ResourceNotFoundException("Member not found"));

        mockMvc.perform(put("/members/{id}", 8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoBody)))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_success() throws Exception {
        MemberEntity entity = buildEntity(20L);

        when(memberRepository.findById(entity.getId())).thenReturn(Optional.of(entity));

        mockMvc.perform(MockMvcRequestBuilders.delete("/members/{id}", 20))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_NotFound() throws Exception {
        when(memberRepository.findById(150L)).thenThrow(new ResourceNotFoundException("Member not found"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/members/{id}", 150))
                .andExpect(status().isNotFound());
    }


    private MemberEntity buildEntity(Long id) {
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

    private List<MemberEntity> buildEntityListWithId() {
        List<MemberEntity> memberEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            memberEntities.add(buildEntity(Long.valueOf(i + 1)));
        }
        return memberEntities;
    }

}