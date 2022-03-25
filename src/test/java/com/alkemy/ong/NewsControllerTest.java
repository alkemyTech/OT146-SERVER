package com.alkemy.ong;

import com.alkemy.ong.data.entities.NewsEntity;
import com.alkemy.ong.data.repositories.NewsRepository;
import com.alkemy.ong.web.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.List;
import java.util.Optional;


import static com.alkemy.ong.web.controllers.NewsController.*;
import static java.util.Arrays.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    NewsRepository newsRepository;

    @Test
    public void createNews_sucess() throws Exception{
        NewsDTO newsDTO = buildNewsDTO();
        NewsEntity newsNotId = buildEntityNews(null);
        NewsEntity newsWithId = buildEntityNews(1L);

        when(newsRepository.save(newsNotId)).thenReturn(newsWithId);
        mockMvc.perform(post("/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("TestNew")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", is("TestContent")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image", is("TestImagen")));
    }

    @Test
    public void createNews_BadRequest() throws Exception{
        NewsDTO newsDTO = buildNewsDTO();
        newsDTO.setName(null);
        newsDTO.setContent(null);

        mockMvc.perform(post("/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getNews() throws Exception{
        List<NewsEntity> newsList = asList(buildEntityNews((1L)), buildEntityNews(2L), buildEntityNews(3L));

        when(newsRepository.findByDeleted(ArgumentMatchers.eq(false), ArgumentMatchers.any(Pageable.class))).thenReturn(new PageImpl<>(newsList));
        mockMvc.perform(get("/news?page=0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("content").isArray());
    }

    @Test
    public void getNewsById() throws Exception{
        NewsDTO newsDTO = buildNewsDTO();
        NewsEntity newsEntity = buildEntityNews(1L);

        when(newsRepository.findById(1L)).thenReturn(Optional.of(newsEntity));
        mockMvc.perform(get("/news/1").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",is("TestNew")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", is("TestContent")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image",is("TestImagen")));
    }

    @Test
    public void updateNews_sucess() throws Exception{
        NewsDTO newsDTO = buildNewsDTO();
        NewsEntity newsEntity = buildEntityNews(5L);

        when(newsRepository.findById(5L)).thenReturn(Optional.of(newsEntity));
        when(newsRepository.save(newsEntity)).thenReturn(newsEntity);

        mockMvc.perform(put("/news/5").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",is("TestNew")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", is("TestContent")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image",is("TestImagen")));
    }

    @Test
    public void updateNews_BadRequest() throws Exception{
        NewsDTO newsDTO = NewsDTO.builder().name(null).image(null).build();
        NewsEntity newsEntity = buildEntityNews(5L);

        when(newsRepository.findById(5L)).thenReturn(Optional.of(newsEntity));
        when(newsRepository.save(newsEntity)).thenReturn(newsEntity);

        mockMvc.perform(put("/news/{id}", 5)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateNews_NotFound() throws Exception{
        NewsDTO newsDTO = buildNewsDTO();

        when(newsRepository.findById(8L)).thenThrow(new ResourceNotFoundException("News not found"));

        mockMvc.perform(put("/news/{id}", 8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteNews_sucess() throws Exception{
        NewsEntity newsEntity = buildEntityNews(20L);
        when(newsRepository.findById(newsEntity.getId())).thenReturn(Optional.of(newsEntity));

        mockMvc.perform(MockMvcRequestBuilders.delete("/news/{id}", 20))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteNews_Failed() throws Exception{
        when(newsRepository.findById(150L)).thenThrow(new ResourceNotFoundException("News not found"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/news/{id}", 150))
                .andExpect(status().isNotFound());
    }

    private NewsEntity buildEntityNews (Long id){
        return NewsEntity.builder()
                .id(id)
                .name("TestNew")
                .content("TestContent")
                .image("TestImagen")
                .build();
    }

    private NewsDTO buildNewsDTO() {
        return NewsDTO.builder()
                .name("TestNew")
                .content("TestContent")
                .image("TestImagen")
                .build();
    }


}
