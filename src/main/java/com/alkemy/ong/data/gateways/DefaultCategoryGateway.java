package com.alkemy.ong.data.gateways;


import com.alkemy.ong.data.repository.CategoryRepository;
import com.alkemy.ong.domain.CategoryGateway;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultCategoryGateway implements CategoryGateway {

    @Autowired
    CategoryRepository repoCategory; 


    @Override
    public Iterable<Object[]> getAllCategories() {
        return repoCategory.getAllCategories();
    }


    
}
