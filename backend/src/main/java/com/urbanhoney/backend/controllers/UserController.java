package com.urbanhoney.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urbanhoney.backend.services.AuthService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/urbanhoney/user")
public class UserController {

    @Autowired
    private AuthService authService;
    
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable("userId") Integer userId) {
        return authService.findUserById(userId);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllUser(Authentication authentication) {
        return authService.getAllUsers(authentication);
    }

    @PutMapping("/revoke/{userId}")
    public ResponseEntity<Map<String, String>> revokeAdminStatus(@PathVariable("userId") Integer userId, Authentication authentication) {
        return authService.revokeAdminStatus(userId, authentication);
    }

    @PutMapping("/set/{userId}")
    public ResponseEntity<Map<String, String>> setAdminStatus(@PathVariable("userId") Integer userId, Authentication authentication) {
        return authService.setAdminStatus(userId, authentication);
    }
    
}
