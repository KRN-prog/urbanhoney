package com.urbanhoney.backend.configuration;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtil {
    
    public String extractToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        // Vérifie si le Header Authorization est présent et commence bien par "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Supprime "Bearer " et garde uniquement le token
        }
        return null; // Retourne null si aucun token trouvé
    }
    
}
