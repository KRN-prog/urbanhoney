package com.urbanhoney.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urbanhoney.backend.services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/urbanhoney/user")
public class UserController {

    @Autowired
    private AuthService authService;
    
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Integer userId) {
        return authService.findUserById(userId);
    }
    
}
