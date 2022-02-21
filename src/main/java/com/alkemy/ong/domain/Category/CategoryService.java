package com.alkemy.ong.domain.Category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService  {

    @Autowired
    CategoryGateway categoryGateway;


    public List<Category> findAll() {
        
        return categoryGateway.findAll();
    }
    
}
