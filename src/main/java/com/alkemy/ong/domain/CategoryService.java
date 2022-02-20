package com.alkemy.ong.domain;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService  {

    @Autowired
    CategoryGateway categoryGateway;


    public Iterable<Object[]> getAllCategories() {
        
        return categoryGateway.getAllCategories();
    }
    
}
