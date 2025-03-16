package com.urbanhoney.backend.services;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import com.urbanhoney.backend.configuration.JwtUtil;
import com.urbanhoney.backend.models.UserEntity;
import com.urbanhoney.backend.repository.AuthRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class IsAdminService {
    private final JwtUtil jwtUtil;
    private final JwtDecoder jwtDecoder;
    private final AuthRepository authRepository;

    public IsAdminService(JwtUtil jwtUtil, JwtDecoder jwtDecoder, AuthRepository authRepository) {
        this.jwtUtil = jwtUtil;
        this.jwtDecoder = jwtDecoder;
        this.authRepository = authRepository;
    }

    public boolean isAdmin(HttpServletRequest request) {
        String token = jwtUtil.extractToken(request);
        Jwt jwt = jwtDecoder.decode(token);
        UserEntity authenticateUser = authRepository.findByEmail(jwt.getSubject()).orElse(null);

        return authenticateUser != null && authenticateUser.getIsAdmin();
    }

    public boolean isAuth(HttpServletRequest requestAuth) {
        String token = jwtUtil.extractToken(requestAuth);
        Jwt jwt = jwtDecoder.decode(token);
        UserEntity authenticateUser = authRepository.findByEmail(jwt.getSubject()).orElse(null);

        return authenticateUser != null && authenticateUser.getIsAdmin();
    }
}
