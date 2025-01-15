package com.urbanhoney.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urbanhoney.backend.services.AuthService;
import com.urbanhoney.backend.usecase.dto.UserDto;
import com.urbanhoney.backend.usecase.dto.request.AuthRequestDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/urbanhoney/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequestDto, Authentication authentication) {
        return authService.loginUser(authRequestDto, authentication);
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        return authService.registerUser(userDto);
    }
    
    @GetMapping("/me")
    public UserDto createUserMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authService.findUserByMail(authentication.getName());
    }
    
}
