package com.alkemy.ong.web.controller;

import com.alkemy.ong.data.entity.OrganizationEntity;
import com.alkemy.ong.data.entity.SlidesEntity;
import com.alkemy.ong.data.repository.OrganizationRepository;
import com.alkemy.ong.web.controller.OrganizationController.OrganizationDto;
import com.alkemy.ong.web.controller.OrganizationController.OrganizationSimpleDto;
import com.alkemy.ong.web.exceptions.BadRequestException;
import com.alkemy.ong.web.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrganizationControllerTest {

    private static String ROOT_PATH = "http://localhost:8080/organization";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrganizationRepository ongRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void get_success() throws Exception{
        OrganizationSimpleDto dtoSimple = buildSimpleDto();
        OrganizationEntity resultEntity = buildEntity( 1L);
        when(ongRepository.findById(1L)).thenReturn(Optional.of(resultEntity));

        mockMvc.perform(get(ROOT_PATH + "/public/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoSimple)))
                .andExpect(status().isOk());
    }

    @Test
    void get_NotFound()throws Exception{
        OrganizationSimpleDto dtoSimple = buildSimpleDto();
        when(ongRepository.findById(5L)).thenThrow(new ResourceNotFoundException("Organization not found"));

        mockMvc.perform(get(ROOT_PATH + "/public/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoSimple)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update_success() throws Exception {
        OrganizationDto dtoBody = buildDto(1L);
        OrganizationEntity resultEntity = buildEntity(1L);

        when(ongRepository.findById(1L)).thenReturn(Optional.of(resultEntity));
        when(ongRepository.save(resultEntity)).thenReturn(resultEntity);

        mockMvc.perform(put(ROOT_PATH + "/public/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoBody)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("idOrganization").value("1"))
                .andExpect(jsonPath("name").value("Test O-N-G"))
                .andExpect(jsonPath("image").value("https://www.testong.com/image.png"))
                .andExpect(jsonPath("address").value("Test 123"))
                .andExpect(jsonPath("phone").value("1231231666"))
                .andExpect(jsonPath("email").value("testong@mail.com"))
                .andExpect(jsonPath("about_us_text").value("Unitary test organization"))
                .andExpect(jsonPath("welcome_text").value("Hello World"))
                .andExpect(jsonPath("createdAt").value("2022-03-12T00:00:00"))
                .andExpect(jsonPath("updatedAt").value("2022-03-12T00:00:00"))
                .andExpect(jsonPath("deleted").value("false"))
                .andExpect(jsonPath("facebookLink").value("https://www.facebook.com/testong"))
                .andExpect(jsonPath("instagramLink").value("https://www.instagram.com/testong"))
                .andExpect(jsonPath("linkedinLink").value("https://www.linkedin.com/testong"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update_NotFound() throws Exception {
        OrganizationDto dtoBody = buildDto(2L);

        when(ongRepository.findById(2L)).thenThrow(new ResourceNotFoundException("Organization not found"));

        mockMvc.perform(put(ROOT_PATH + "/public/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoBody)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update_BadRequest() throws Exception{
        OrganizationDto dtoBody = OrganizationDto.builder()
                .name(null)
                .image(null)
                .email(null)
                .welcome_text(null)
                .build();

       OrganizationEntity resultEntity = buildEntity(5L);

        when(ongRepository.findById(5L)).thenReturn(Optional.of(resultEntity));
        when(ongRepository.save(resultEntity)).thenThrow(new BadRequestException("Some of the required fields are empty"));

        mockMvc.perform(put(ROOT_PATH + "/public/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoBody)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update_BadRequestOneField() throws Exception{
        OrganizationDto dtoBody = OrganizationDto.builder()
                .name(null)
                .build();

        OrganizationEntity resultEntity = buildEntity(5L);

        when(ongRepository.findById(5L)).thenReturn(Optional.of(resultEntity));
        when(ongRepository.save(resultEntity)).thenThrow(new BadRequestException("The name field is required"));

        mockMvc.perform(put(ROOT_PATH + "/public/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoBody)))
                .andExpect(status().isBadRequest());
    }


    private OrganizationEntity buildEntity(long idOrganization) {
        OrganizationEntity ong = OrganizationEntity.builder()
                .idOrganization(idOrganization)
                .name("Test O-N-G")
                .image("https://www.testong.com/image.png")
                .address("Test 123")
                .phone(1231231666)
                .email("testong@mail.com")
                .about_us_text("Unitary test organization")
                .welcome_text("Hello World")
                .createdAt(LocalDateTime.parse("2022-03-12T00:00:00"))
                .updatedAt(LocalDateTime.parse("2022-03-12T00:00:00"))
                .deleted(false)
                .facebookLink("https://www.facebook.com/testong")
                .instagramLink("https://www.instagram.com/testong")
                .linkedinLink("https://www.linkedin.com/testong")
                .build();
        List<SlidesEntity> slides = new ArrayList();
        ong.setSlides(slides);
        return ong;
    }

    private OrganizationDto buildDto(long idOrganization) {
        OrganizationDto ong = OrganizationDto.builder()
                .name("Test O-N-G")
                .image("https://www.testong.com/image.png")
                .address("Test 123")
                .phone(1231231666)
                .email("testong@mail.com")
                .about_us_text("Unitary test organization")
                .welcome_text("Hello World")
                .createdAt(LocalDateTime.parse("2022-03-12T00:00:00"))
                .updatedAt(LocalDateTime.parse("2022-03-12T00:00:00"))
                .deleted(false)
                .facebookLink("https://www.facebook.com/testong")
                .instagramLink("https://www.instagram.com/testong")
                .linkedinLink("https://www.linkedin.com/testong")
                .build();
        ong.setIdOrganization(idOrganization);
        List<SlidesController.SimpleSlideDto> slides = new ArrayList();
        ong.setSlides(slides);
        return ong;
    }

    private OrganizationSimpleDto buildSimpleDto(){
        OrganizationSimpleDto simpleDto = OrganizationSimpleDto.builder()
                .name("Test O-N-G")
                .image("https://www.testong.com/image.png")
                .address("Test 123")
                .phone(1231231666)
                .facebookLink("https://www.facebook.com/testong")
                .instagramLink("https://www.instagram.com/testong")
                .linkedinLink("https://www.linkedin.com/testong")
                .build();
        List<SlidesController.SimpleSlideDto> slides = new ArrayList();
        simpleDto.setSlides(slides);
        return simpleDto;
    }
}
