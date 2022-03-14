package com.alkemy.ong;

import com.alkemy.ong.data.entity.NewsEntity;
import com.alkemy.ong.data.repository.NewsRepository;
import com.alkemy.ong.web.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import javax.print.attribute.standard.Media;
import java.util.List;
import java.util.Optional;


import static com.alkemy.ong.web.controller.NewsController.*;
import static java.util.Arrays.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("TestImagen")));
    }

    @Test
    public void createNews_BadRequest() throws Exception{
        NewsDTO newsDTO = buildNewsDTO();
        newsDTO.setName(null);
        newsDTO.setContent(null);
        NewsEntity newsNotId = buildEntityNews(null);
        NewsEntity newsWithId = buildEntityNews(1L);

        when(newsRepository.save(newsNotId)).thenReturn(newsWithId);
        mockMvc.perform(post("/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getNews() throws Exception{
        List<NewsEntity> newsList = asList(buildEntityNews((1L)), buildEntityNews(2L), buildEntityNews(3L));

        when(newsRepository.findAll()).thenReturn(newsList);
        mockMvc.perform(get("/news/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", hasSize(3)));
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",is("TestName")))
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
