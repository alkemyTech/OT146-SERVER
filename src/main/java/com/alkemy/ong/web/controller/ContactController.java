package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.contacts.Contact;
import com.alkemy.ong.domain.contacts.ContactService;
import com.alkemy.ong.domain.mail.MailGateway;
import com.alkemy.ong.domain.mail.MailService;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;
    private final MailService mailService;

    public ContactController(ContactService contactService, MailService mailService) {
        this.contactService = contactService;
        this.mailService = mailService;
    }

    @PostMapping
    public ResponseEntity<ContactDTO> create(@Valid @RequestBody ContactDTO contactDTO){
        String to = contactDTO.getEmail();
        String subject = "You’ve made a new contact";
        String title = "Your contact details have been added!";
        String body = contactDTO.getMessage();
        mailService.sendMailWithTemplate(to, subject, title, body);
        Contact contact = contactService.create(toDomain(contactDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(contact));
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> findAll(){
        List<Contact> contactsList = contactService.findAll();
        List<ContactController.ContactDTO> dtoList = toDtoList(contactsList);
        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> findById(@PathVariable Long id){
        Contact contact = contactService.findById(id);
        ContactController.ContactDTO contactDTO = toDTO(contact);
        return ResponseEntity.ok().body(contactDTO);
    }

    private Contact toDomain(ContactDTO contactDTO){
        return Contact.builder()
                .id(contactDTO.getId())
                .name(contactDTO.getName())
                .phone(contactDTO.getPhone())
                .email(contactDTO.getEmail())
                .message(contactDTO.getMessage())
                .build();
    }

    private ContactDTO toDTO(Contact contact){
        return ContactDTO.builder()
                .id(contact.getId())
                .name(contact.getName())
                .phone(contact.getPhone())
                .email(contact.getEmail())
                .message(contact.getMessage())
                .createdAt(contact.getCreatedAt())
                .updatedAt(contact.getUpdatedAt())
                .build();
    }

    private List<ContactController.ContactDTO> toDtoList(List<Contact> contacts) {
        return contacts.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Data
    @Builder
    public static class ContactDTO{
        private Long id;

        @NotNull
        @NotEmpty
        private String name;
        private String phone;

        @NotNull
        @NotEmpty
        private String email;
        private String message;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
