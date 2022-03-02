package com.alkemy.ong.domain.contacts;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService implements ContactGateway{

    private final ContactGateway contactGateway;

    public ContactService(ContactGateway contactGateway) {
        this.contactGateway = contactGateway;
    }

    @Override
    public Contact create(Contact contact) {
        return contactGateway.create(contact);
    }

    @Override
    public List<Contact> findAll() {
        return contactGateway.findAll();
    }
}
