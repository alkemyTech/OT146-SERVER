package com.alkemy.ong.domain.contacts;


import java.util.List;

public interface ContactGateway {
    Contact create (Contact contact);
    List<Contact> findAll();
    Contact findById(Long id);
}
