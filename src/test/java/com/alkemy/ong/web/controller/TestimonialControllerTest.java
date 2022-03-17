package com.alkemy.ong.web.controller;

import com.alkemy.ong.data.entity.TestimonialEntity;
import com.alkemy.ong.data.repository.TestimonialRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;
import static com.alkemy.ong.web.controller.TestimonialController.TestimonialDTO;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;
@SpringBootTest
@AutoConfigureMockMvc
class TestimonialControllerTest {
    static String apiRootPath = "http://localhost:8080/testimonials";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TestimonialRepository testimonialRepository;


    @BeforeEach
    public void setUp(){
        TestimonialEntity testimonialA = buildTestimonialEntity(1L, "A", "http://...", "content", false);
        TestimonialEntity testimonialB = buildTestimonialEntity(2L, "B", "http://...", "content", false);
        TestimonialEntity testimonialC = buildTestimonialEntity(3L, "C", "http://...", "content", false);
        TestimonialEntity testimonialNew = buildTestimonialEntity(null, "E", "http://...", "content");
        TestimonialEntity testimonialCreated = buildTestimonialEntity(4L, "E", "http://...", "content", true);
        when(testimonialRepository.findById(1L)).thenReturn(Optional.of(testimonialA));
        when(testimonialRepository.findById(2L)).thenReturn(Optional.of(testimonialB));
        when(testimonialRepository.findById(3L)).thenReturn(Optional.of(testimonialC));
        when(testimonialRepository.findById(4L)).thenReturn(Optional.empty());

        when(testimonialRepository.findByDeleted(eq(false), any())).thenReturn(new PageImpl(Arrays.asList(testimonialA,testimonialB, testimonialC)));
        when(testimonialRepository.save(testimonialNew)).thenReturn(testimonialCreated);
    }


    @Test
    public void saveTestimonial() throws Exception{
        var testimonialDTO = buildTestimonialDTO(null, "E", "http://...", "content");
        mockMvc.perform(post(apiRootPath)
                        .content(convertToStringJson(testimonialDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("id").value(4L))
                .andExpect(jsonPath("name").value("E"))
                .andExpect(jsonPath("image").value("http://..."))
                .andExpect(jsonPath("content").value("content"))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void saveTestimonialBadRequest() throws Exception{
        var testimonialDTO = buildTestimonialDTO(null, null, "", "content");
        mockMvc.perform(post(apiRootPath)
                        .content(convertToStringJson(testimonialDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void getPage() throws Exception {
        mockMvc.perform(get(apiRootPath + "?page=0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").isArray())
                .andExpect(jsonPath("nextPage").value(""))
                .andExpect(jsonPath("previousPage").value(""))
                .andReturn().getResponse().getContentAsString();
    }

    private void configRemoveTestimonialTest() {
        TestimonialEntity testimonial = buildTestimonialEntity(1L, "A", "http://...", "content", false);
        TestimonialEntity testimonialSave = buildTestimonialEntity(1L, "A", "http://...", "content", true);
        when(testimonialRepository.findById(1L)).thenReturn(Optional.of(testimonial));
        when(testimonialRepository.save(testimonialSave)).thenReturn(testimonialSave);
    }

    @Test
    public void removeTestimonial() throws Exception {
        configRemoveTestimonialTest();

        mockMvc.perform(delete(apiRootPath + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void removeTestimonialNotFound() throws Exception {
        configRemoveTestimonialTest();

        mockMvc.perform(delete(apiRootPath + "/4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void updateTestimonials() throws Exception {
        var testimonialDTO = buildTestimonialDTO(1L, "A", "https://...", "content");
        configUpdateTestimonialsTest();

        mockMvc.perform(put(apiRootPath + "/1")
                        .content(convertToStringJson(testimonialDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("A"))
                .andExpect(jsonPath("image").value("https://..."))
                .andExpect(jsonPath("content").value("content"))
                .andReturn().getResponse().getContentAsString();
    }

    private void configUpdateTestimonialsTest() {
        TestimonialEntity testimonialEntity = buildTestimonialEntity(1L,"A","https://...","content",false);
        when(testimonialRepository.save(testimonialEntity)).thenReturn(testimonialEntity);
    }

    private TestimonialDTO buildTestimonialDTO(Long id, String name, String image, String content){
        return TestimonialDTO.builder()
                .id(id)
                .name(name)
                .image(image)
                .content(content)
                .build();
    }
    private TestimonialEntity buildTestimonialEntity(Long id, String name, String image, String content){
        return TestimonialEntity.builder()
                .id(id)
                .name(name)
                .image(image)
                .content(content)
                .build();
    }

    private TestimonialEntity buildTestimonialEntity(Long id, String name, String image, String content, Boolean deleted){
        TestimonialEntity testimonialEntity = buildTestimonialEntity(id, name, image, content);
        testimonialEntity.setDeleted(deleted);
        return testimonialEntity;
    }

    private String convertToStringJson(Object testimonialDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(testimonialDTO);
    }
}
