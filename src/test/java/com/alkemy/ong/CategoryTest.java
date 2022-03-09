package com.alkemy.ong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.alkemy.ong.data.entity.CategoryEntity;
import com.alkemy.ong.data.repository.CategoryRepository;
import com.alkemy.ong.web.controller.CategoryController;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest(CategoryController.class)
public class CategoryTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MessageSource messageSource;


    @Test
    void createCategoryTest() throws Exception {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("RRHH");
        categoryEntity.setDescription("Categoria de RRHH");
        categoryEntity.setDeleted(false);
        String category = objectMapper.writeValueAsString(categoryEntity);

        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);


        mockMvc.perform(post("/ong/categories").contentType(MediaType.APPLICATION_JSON).content(category));
        
        ResponseEntity<?> response = ResponseEntity.ok().body(categoryEntity);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryEntity, response.getBody());

    }

    @Test
    void deleteCategoryTest() throws Exception {

        when(categoryRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/ong/categories/1")).andExpect(status().isNoContent());
    }

    @Test
    void deleteCategoryTestWrongId() throws Exception {

        when(categoryRepository.existsById(-1L)).thenReturn(false);

        mockMvc.perform(delete("/ong/categories/500")).andExpect(status().isNotFound());

        String message = messageSource.getMessage("Id not found",
        null, null);

        ResponseEntity <?> responseEntity = new ResponseEntity(message, HttpStatus.NOT_FOUND);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(message, responseEntity.getBody());
    }

    @Test
    void listCategoriesTest() throws Exception {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("Marketing");
        categoryEntity.setDescription("Categoria de marketing");
        categoryEntity.setDeleted(false);
        String category = objectMapper.writeValueAsString(categoryEntity);

        CategoryEntity categoryEntity2 = new CategoryEntity();
        categoryEntity2.setName("Salud");
        categoryEntity2.setDescription("categoria de salud");
        categoryEntity2.setDeleted(false);
        String category2 = objectMapper.writeValueAsString(categoryEntity2);

        List<CategoryEntity> ce = new ArrayList<>();
        ce.add(categoryEntity);
        ce.add(categoryEntity2);
        
        when(categoryRepository.findAll()).thenReturn(ce);

        mockMvc.perform(get("/ong/categories")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Marketing")))
                .andExpect(jsonPath("$[1].name", is("Salud")));
   
    }

    @Test
    void updateCategoryTest() throws Exception { 

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("Marketing");
        categoryEntity.setDescription("Categoria de marketing");
        categoryEntity.setDeleted(false);
        String category = objectMapper.writeValueAsString(categoryEntity);

        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);

        mockMvc.perform(put("/ong/categories/1").contentType(MediaType.APPLICATION_JSON));

        CategoryEntity categoryEntity2 = new CategoryEntity();
        categoryEntity2.setName(categoryEntity.getName());
        categoryEntity2.setDescription(categoryEntity.getDescription());
        categoryEntity2.setDeleted(categoryEntity.getDeleted());
        String category2 = objectMapper.writeValueAsString(categoryEntity2);

        ResponseEntity<?> response = new ResponseEntity(categoryEntity2, HttpStatus.OK);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryEntity2, response.getBody());
        assertEquals(categoryEntity2.getDescription(), categoryEntity2.getDescription());
        assertEquals(categoryEntity2.getName(), categoryEntity.getName());
        




    }
    
}
