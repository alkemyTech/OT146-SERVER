package com.alkemy.ong.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerController {
    @RequestMapping("/api/document")
    public String getSwaggerUi(){
        return "redirect:/swagger-ui.html";
    }
}
