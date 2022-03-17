package com.alkemy.ong.web.controller;

import com.alkemy.ong.data.entity.ContactEntity;
import com.alkemy.ong.data.repository.ContactRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.alkemy.ong.web.controller.ContactController.ContactDTO;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerTest {
    static String apiRootPath = "http://localhost:8080/contacts";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ContactRepository  contactRepository;

    @BeforeEach
    void setUp() {
        ContactEntity contactNew = buildContactEntity(null, "Pablo", "2611111111", "test1A@email.com","A");
        ContactEntity contactReturn = buildContactEntityWithDate(2L, "Pablo", "2611111111", "test1A@email.com","A");
        when(contactRepository.save(contactNew)).thenReturn(contactReturn);
        when(contactRepository.findById(2L)).thenReturn(Optional.of(contactReturn));
        when(contactRepository.findById(3L)).thenReturn(Optional.empty());
        List<ContactEntity> contactEntityList = Arrays.asList(
                buildContactEntityWithDate(1L, "A", "2611111111", "testA@email.com","A"),
                buildContactEntityWithDate(2L, "B", "2611111111", "testB@email.com","B")
        );
        when(contactRepository.findAll()).thenReturn(contactEntityList);
    }

    private ContactEntity buildContactEntity(Long id, String name, String phone, String email, String message) {
        return ContactEntity.builder()
                .id(id)
                .name(name)
                .email(email)
                .message(message)
                .phone(phone)
                .updatedAt(null)
                .createdAt(null)
                .build();
    }
    private ContactEntity buildContactEntityWithDate(Long id, String name, String phone, String email, String message ) {
        ContactEntity contact = buildContactEntity(id, name, phone, email, message);
        contact.setCreatedAt(defaultDate());
        return contact;
    }

    @Test
    public void saveContactTest() throws Exception {
        var contactDto = buildContactDto(null, "Pablo", "2611111111", "test1A@email.com","A");
        mockMvc.perform(post(apiRootPath)
                    .content(convertToStringJson(contactDto))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("id").value("2"))
                .andExpect(jsonPath("name").value("Pablo"))
                .andExpect(jsonPath("phone").value("2611111111"))
                .andExpect(jsonPath("email").value("test1A@email.com"))
                .andExpect(jsonPath("message").value("A"))
                .andExpect(jsonPath("createdAt").value(formatDate(defaultDate())))
                .andExpect(jsonPath("updatedAt").isEmpty())
                .andReturn().getResponse();
    }

    @Test
    public void saveContactBadRequesTest() throws Exception {
        var contactDto = buildContactDto(null, "Pablo", "2611111111", null,"A");
        mockMvc.perform(post(apiRootPath)
                        .content(convertToStringJson(contactDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();
    }

    @Test
    public void getContactByIdTest() throws Exception {
        mockMvc.perform(get(apiRootPath+ "/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("id").value("2"))
                .andExpect(jsonPath("name").value("Pablo"))
                .andExpect(jsonPath("phone").value("2611111111"))
                .andExpect(jsonPath("email").value("test1A@email.com"))
                .andExpect(jsonPath("message").value("A"))
                .andExpect(jsonPath("createdAt").value(formatDate(defaultDate())))
                .andExpect(jsonPath("updatedAt").isEmpty())
                .andReturn().getResponse();
    }

    @Test
    public void getContactNotFound() throws Exception {
        mockMvc.perform(get(apiRootPath+ "/3"))
                .andExpect(status().isNotFound())
                .andReturn().getResponse();
    }

    @Test
    public void getAllContact() throws Exception {
        mockMvc.perform(get(apiRootPath))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("A"))
                .andExpect(jsonPath("$[0].phone").value("2611111111"))
                .andExpect(jsonPath("$[0].email").value("testA@email.com"))
                .andExpect(jsonPath("$[0].message").value("A"))
                .andExpect(jsonPath("$[0].createdAt").value(formatDate(defaultDate())))
                .andExpect(jsonPath("$[0].updatedAt").isEmpty())
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("B"))
                .andExpect(jsonPath("$[1].phone").value("2611111111"))
                .andExpect(jsonPath("$[1].email").value("testB@email.com"))
                .andExpect(jsonPath("$[1].message").value("B"))
                .andExpect(jsonPath("$[1].createdAt").value(formatDate(defaultDate())))
                .andExpect(jsonPath("$[1].updatedAt").isEmpty())
                .andReturn().getResponse();
    }

    private ContactDTO buildContactDto(Long id, String name, String phone, String email, String message) {
        return ContactDTO.builder()
                .id(id)
                .name(name)
                .email(email)
                .message(message)
                .phone(phone)
                .build();
    }

    private String convertToStringJson(Object testimonialDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(testimonialDTO);
    }
    private LocalDateTime defaultDate(){
        return LocalDateTime.of(2021,10,2,0,0);
    }
    private String formatDate(LocalDateTime localDateTime){
        return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}