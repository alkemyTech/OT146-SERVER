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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                .name("Marcelo Iniqual")
                .image("https://s3.us-east-1.amazonaws.com/cohorte-febrero-b35bfd02/2022-03-04T20:09:32.426-foto.jpg")
                .content("Contenido")
                .build();
        TestimonialEntity testimonialR = TestimonialEntity.builder()
                .id(2L)
                .name("Marcelo Iniqual")
                .image("https://s3.us-east-1.amazonaws.com/cohorte-febrero-b35bfd02/2022-03-04T20:09:32.426-foto.jpg")
                .content("Contenido")
                .build();

        Mockito.when(testimonialRepository.save(ArgumentMatchers.any(TestimonialEntity.class))).thenReturn(testimonialR);
        String response = mockMvc.perform(
                post(apiRootPath + "/testimonials")
                    .content(objectMapper.writeValueAsString(testimonialDTO))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("name").value("Marcelo Iniqual"))
                .andExpect(jsonPath("image").value("https://s3.us-east-1.amazonaws.com/cohorte-febrero-b35bfd02/2022-03-04T20:09:32.426-foto.jpg"))
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
        Mockito.when(testimonialRepository.findByDeleted(ArgumentMatchers.eq(false), ArgumentMatchers.anyObject())).thenReturn(page);
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
        TestimonialEntity testimonial = TestimonialEntity.builder().id(1L).name("A").image("http://...").content("asd").deleted(true).build();
        Mockito.when(testimonialRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(testimonial));
        Mockito.when(testimonialRepository.save(testimonial)).thenReturn(testimonial);
        String response = mockMvc.perform(
                        delete(apiRootPath + "/testimonials/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn().getResponse().getContentAsString();
        System.out.println(response);
    }
}