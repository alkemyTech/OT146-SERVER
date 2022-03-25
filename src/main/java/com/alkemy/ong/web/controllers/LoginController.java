package com.alkemy.ong.web.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @ApiOperation("Login.")
    @PostMapping("/login")
    public void fakeLogin(@ApiParam("User") @RequestParam String username,
                          @ApiParam(value = "Password") @RequestParam String password) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }
}
