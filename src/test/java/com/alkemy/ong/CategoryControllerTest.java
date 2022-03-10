package com.alkemy.ong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.alkemy.ong.data.entity.CategoryEntity;
import com.alkemy.ong.data.repository.CategoryRepository;
import com.alkemy.ong.web.controller.CategoryController;
import com.alkemy.ong.web.exceptions.ResourceNotFoundException;
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
public class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void createCategoryTest() throws Exception {

        CategoryEntity ce = categoryRepository.findById((long) 6).orElseThrow(()-> new ResourceNotFoundException("id not found")); 

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("RRHH");
        categoryEntity.setDescription("Categoria de RRHH");
        categoryEntity.setDeleted(false);

        when(categoryRepository.save(categoryEntity)).thenReturn(ce);


        mockMvc.perform(post("/ong/categories").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(categoryEntity)))
            .andExpect(status().isCreated());        

    }

    @Test
    void deleteCategoryTest() throws Exception {

        when(categoryRepository.existsById((long) 5)).thenReturn(true);
        when(categoryRepository.findById(5)).thenReturn(Optional.of(buildEntity(5, "RRHH", "category of RRHH")));

        mockMvc.perform(delete("/ong/categories/5")).andExpect(status().isNoContent()); 
    }

    private buildEntity(Long id, String name, String description) {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(id);
        categoryEntity.setName(name);
        categoryEntity.setDescription(description);
        categoryEntity.setDeleted(false);

        
    }
    @Test
    void deleteCategoryTestWrongId() throws Exception {

        doThrow(ResourceNotFoundException.class).when(categoryRepository).deleteById(1L);

        mockMvc.perform(delete("/ong/categories/1")).andExpect(status().isNotFound());
    }

    @Test
    void listCategoriesTest() throws Exception {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("Marketing");
        categoryEntity.setDescription("Categoria de marketing");
        categoryEntity.setDeleted(false);

        CategoryEntity categoryEntity2 = new CategoryEntity();
        categoryEntity2.setName("Salud");
        categoryEntity2.setDescription("categoria de salud");
        categoryEntity2.setDeleted(false);

        List<CategoryEntity> ce = new ArrayList<>();
        ce.add(categoryEntity);
        ce.add(categoryEntity2);
        
        when(categoryRepository.findAll()).thenReturn(ce);

        mockMvc.perform(get("/ong/categories").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(categoryEntity)))
                .andExpect(status().isOk())
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

        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);

        CategoryEntity categoryEntity2 = new CategoryEntity();
        categoryEntity2.setName(categoryEntity.getName());
        categoryEntity2.setDescription(categoryEntity.getDescription());
        categoryEntity2.setDeleted(categoryEntity.getDeleted());

        mockMvc.perform(put("/ong/categories/6").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(categoryEntity2)))
                .andExpect(status().isOk());

    }
    
}
