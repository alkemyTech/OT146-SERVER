package com.alkemy.ong;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.alkemy.ong.data.entity.CategoryEntity;
import com.alkemy.ong.data.repository.CategoryRepository;
import com.alkemy.ong.web.controller.CategoryController;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategoryRepository categoryRepository;

    ObjectMapper objectMapper;

    @Test
    void createCategoryTest() throws Exception {

        CategoryEntity categoryEntity = new CategoryEntity();

        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);

        mockMvc.perform(post("/ong/categories").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryEntity)
                        ))
                    .andExpect(status().isCreated)
                    .andExpect(jsonPath("name").isNotEmpty())
                    .andExpect(jsonPath("description").isNotEmpty())
                    .andExpect(jsonPath("deleted").isNotEmpty())
                    .andExpect(jsonPath("createdAt").isNotEmpty())
                    .andExpect(jsonPath("updatedAt").isNotEmpty());

    }

    @Test
    void deleteCategoryTest() throws Exception {

        when(categoryRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/ong/categories/1")).andExpect(status().isNoContent());
    }

    @Test
    void listCategoriesTest() throws Exception {

        List<CategoryEntity> ce = new ArrayList<>();
        
        when(categoryRepository.findAll()).thenReturn(ce);

        mockMvc.perform(get("/ong/categories")).contentType(MediaType.APPLICATION_JSON)
                .andExpect(jsonPath(hasSize(3)));
    }
    
}
