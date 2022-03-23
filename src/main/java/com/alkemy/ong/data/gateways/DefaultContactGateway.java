package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entity.ContactEntity;
import com.alkemy.ong.data.repository.ContactRepository;
import com.alkemy.ong.domain.contacts.Contact;
import com.alkemy.ong.domain.contacts.ContactGateway;
import com.alkemy.ong.web.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class DefaultContactGateway implements ContactGateway {

    private final ContactRepository contactRepository;

    public DefaultContactGateway(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact create(Contact contact) {
        return toModel(contactRepository.save(toEntity(contact)));
    }

    @Override
    public List<Contact> findAll() {
        List<ContactEntity> contactEntityList = contactRepository.findAll();
        return toModelList(contactEntityList);
    }

    @Override
    public Contact findById(Long id) {
        ContactEntity contact = contactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
        return toModel(contact);
    }


    private ContactEntity toEntity(Contact contact) {
        return ContactEntity.builder()
                .id(contact.getId())
                .name(contact.getName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .message(contact.getMessage())
                .build();
    }

    private Contact toModel(ContactEntity contactEntity) {
        return Contact.builder()
                .id(contactEntity.getId())
                .name(contactEntity.getName())
                .phone(contactEntity.getPhone())
                .email(contactEntity.getEmail())
                .phone(contactEntity.getPhone())
                .message(contactEntity.getMessage())
                .createdAt(contactEntity.getCreatedAt())
                .updatedAt(contactEntity.getUpdatedAt())
                .build();
    }

    private List<Contact> toModelList(List<ContactEntity> news) {
        return news.stream().map(this::toModel).collect(toList());
    }
}