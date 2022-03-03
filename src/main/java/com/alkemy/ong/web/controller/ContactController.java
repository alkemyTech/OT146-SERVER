package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.contacts.Contact;
import com.alkemy.ong.domain.contacts.ContactService;
import com.alkemy.ong.domain.news.News;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<ContactDTO> create(@RequestBody ContactDTO contactDTO){
        Contact contact = contactService.create(toDomain(contactDTO));
        return ResponseEntity.ok((toDTO(contact)));
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> findAll(){
        List<Contact> contactsList = contactService.findAll();
        List<ContactController.ContactDTO> dtoList = toDtoList(contactsList);
        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> showId(@PathVariable Long id){
        Contact contact = contactService.findById(id);
        ContactController.ContactDTO contactDTO = toDTO(contact);
        return ResponseEntity.ok().body(contactDTO);
    }

    public Contact toDomain(ContactDTO contactDTO){
        return Contact.builder()
                .id(contactDTO.getId())
                .name(contactDTO.getName())
                .phone(contactDTO.getPhone())
                .email(contactDTO.getEmail())
                .message(contactDTO.getMessage())
                .build();
    }

    public ContactDTO toDTO(Contact contact){
        return ContactDTO.builder()
                .id(contact.getId())
                .name(contact.getName())
                .phone(contact.getPhone())
                .email(contact.getEmail())
                .message(contact.getEmail())
                .createdAt(contact.getCreatedAt())
                .build();
    }

    private List<ContactController.ContactDTO> toDtoList(List<Contact> contacts) {
        return contacts.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Data
    @Builder
    public static class ContactDTO{
        private Long id;
        private String name;
        private String phone;
        private String email;
        private String message;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
