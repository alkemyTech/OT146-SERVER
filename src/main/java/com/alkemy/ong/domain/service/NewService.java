package com.alkemy.ong.domain.service;

import com.alkemy.ong.data.entity.News;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NewService {

    @Autowired
    NewGateway newGateway;

    public List<News> findAll() {
        return newGateway.findAll();
    }

}
