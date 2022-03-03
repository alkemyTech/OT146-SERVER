package com.alkemy.ong.domain.contacts;

import com.alkemy.ong.data.entity.ContactEntity;

import java.util.List;

public interface ContactGateway {
    Contact create (Contact contact);
    List<Contact> findAll();
    Contact findById(Long id);
}
