package com.urbanhoney.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/urbanhoney/user")
public class UserController {
    
    @GetMapping("/{userId}")
    public String getUser() {
        return "Get user by id";
    }
    
}
