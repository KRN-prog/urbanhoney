package com.urbanhoney.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/urbanhoney/auth")
public class AuthController {
    
    @PostMapping("/login")
    public String login() {
        return "Login";
    }
    
    @PostMapping("/register")
    public String register() {
        return "Register";
    }
    
    @GetMapping("/me")
    public String createUserMe() {
        return "Create Me";
    }
    
}
