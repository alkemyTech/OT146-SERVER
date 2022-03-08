package com.alkemy.ong.web.controller;

import com.alkemy.ong.data.entity.TestimonialEntity;
import com.alkemy.ong.data.repository.TestimonialRepository;
import com.alkemy.ong.domain.testimonial.Testimonial;
import com.alkemy.ong.web.utils.PageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;
@SpringBootTest
@AutoConfigureMockMvc
class TestimonialControllerTest {
    String apiRootPath = "http://localhost:8080";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TestimonialRepository testimonialRepository;

    @Test
    public void saveTestimonial() throws Exception{
        TestimonialController.TestimonialDTO testimonialDTO = TestimonialController.TestimonialDTO.builder()
                .id(null)
                .name("Marcelo")
                .image("https://s3.us-ea...")
                .content("Contenido")
                .build();
        TestimonialEntity testimonialS = TestimonialEntity.builder()
                .id(null)
                .name("Marcelo")
                .image("https://s3.us-ea...")
                .content("Contenido")
                .build();
        TestimonialEntity testimonialR = TestimonialEntity.builder()
                .id(2L)
                .name("Marcelo")
                .image("https://s3.us-ea...")
                .content("Contenido")
                .build();

        when(testimonialRepository.save(testimonialS)).thenReturn(testimonialR);
        String response = mockMvc.perform(
                post(apiRootPath + "/testimonials")
                    .content(objectMapper.writeValueAsString(testimonialDTO))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("name").value("Marcelo"))
                .andExpect(jsonPath("image").value("https://s3.us-ea..."))
                .andExpect(jsonPath("content").value("Contenido"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(response);
    }

    @Test
    public void getPage() throws Exception {
        List<TestimonialEntity> testimonials = new ArrayList<>();
        testimonials.add(TestimonialEntity.builder().id(0L).name("A").image("http://...").content("asd").build());
        testimonials.add(TestimonialEntity.builder().id(1L).name("A").image("http://...").content("asd").build());
        testimonials.add(TestimonialEntity.builder().id(2L).name("A").image("http://...").content("asd").build());
        Page<TestimonialEntity> page = new PageImpl(testimonials);
        when(testimonialRepository.findByDeleted(ArgumentMatchers.eq(false), ArgumentMatchers.anyObject())).thenReturn(page);
        String response = mockMvc.perform(
                get(apiRootPath + "/testimonials?page=0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").isArray())
                .andExpect(jsonPath("nextPage").value(""))
                .andExpect(jsonPath("previousPage").value(""))
                .andReturn().getResponse().getContentAsString();
        System.out.println(response);
    }

    @Test
    public void removeTestimonial() throws Exception {
        TestimonialEntity testimonial = TestimonialEntity.builder().id(1L).name("A").image("http://...").content("asd").deleted(false).build();
        TestimonialEntity testimonialSave = TestimonialEntity.builder().id(1L).name("A").image("http://...").content("asd").deleted(true).build();
        when(testimonialRepository.findById(1L)).thenReturn(Optional.of(testimonial));
        when(testimonialRepository.save(testimonialSave)).thenReturn(testimonialSave);
        String response = mockMvc.perform(
                        delete(apiRootPath + "/testimonials/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn().getResponse().getContentAsString();
        System.out.println(response);
    }

    @Test
    public void updateTestimonials() throws Exception {
        TestimonialController.TestimonialDTO testimonialDTO = TestimonialController.TestimonialDTO.builder()
                .id(1L)
                .name("Marcelo")
                .image("https://...")
                .content("Contenido")
                .build();
        TestimonialEntity testimonial = TestimonialEntity.builder().id(1L).name("A").image("https://...").content("asd").deleted(false).build();
        when(testimonialRepository.findById(1L)).thenReturn(Optional.of(testimonial));
        TestimonialEntity testimonialEntity = TestimonialEntity.builder().id(1L).name("Marcelo").image("https://...").content("Contenido").deleted(false).build();
        when(testimonialRepository.save(testimonialEntity)).thenReturn(testimonialEntity);
        String response = mockMvc.perform(
                        put(apiRootPath + "/testimonials/1")
                                .content(objectMapper.writeValueAsString(testimonialDTO))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("name").value("Marcelo"))
                .andExpect(jsonPath("image").value("https://..."))
                .andExpect(jsonPath("content").value("Contenido"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(response);
    }
}