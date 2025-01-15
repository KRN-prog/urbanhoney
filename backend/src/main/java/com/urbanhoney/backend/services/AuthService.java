package com.urbanhoney.backend.services;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.urbanhoney.backend.models.UserEntity;
import com.urbanhoney.backend.repository.AuthRepository;
import com.urbanhoney.backend.usecase.dto.UserDto;
import com.urbanhoney.backend.usecase.dto.mapper.UserMapper;
import com.urbanhoney.backend.usecase.dto.request.AuthRequestDto;
import com.urbanhoney.backend.usecase.dto.response.TokenResponse;

@Service
public class AuthService {

    private AuthRepository authRepository;

    private JWTService jwtService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(AuthRepository authRepository, JWTService jwtService) {
        this.authRepository = authRepository;
        this.jwtService = jwtService;
    }
    
    public Boolean isValidEmail(String email) {
        // Regex pour vérifier si l'email est valide
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    Map<String, String> response = new HashMap<>();

    public ResponseEntity<?> registerUser(UserDto userDto) {
        
        if (userDto.getEmail() != null || isValidEmail(userDto.getEmail()) == true) {
            UserEntity findByEmail = authRepository.findByEmail(userDto.getEmail()).orElse(null);
            UserEntity findByUsername = authRepository.findByUsername(userDto.getUsername()).orElse(null);
            if (findByEmail != null) {
                response.put("error", "This email is already taken !");
                return ResponseEntity.badRequest().body(response);
            } else if (findByUsername != null) {
                response.put("error", "This username is already taken !");
                return ResponseEntity.badRequest().body(response);
            } else {
                Map<String, UserEntity> responseSucess = new HashMap<>();
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
                UserEntity userEntity = UserMapper.mapToUser(userDto);
                authRepository.save(userEntity);
                responseSucess.put("Success", userEntity);
                return ResponseEntity.ok(responseSucess);
            }
        }

        response.put("error", "Please insert a real mail please.");
        return ResponseEntity.badRequest().body(response);
    }


    public ResponseEntity<?> loginUser(AuthRequestDto authRequestDto, Authentication authentication) {
        UserEntity getUserByEmailOrUsername = authRepository.findByEmailOrUsername(authRequestDto.getEmailOrUsername()).orElse(null);
        if (getUserByEmailOrUsername == null) {
            response.put("error", "Email or Username not found.");
            return ResponseEntity.badRequest().body(response);
        }

        if (passwordEncoder.matches(authRequestDto.getPassword(), getUserByEmailOrUsername.getPassword()) == false) {
            response.put("error", "Your password is incorrect.");
            return ResponseEntity.badRequest().body(response);
        }

        UserDto userDto = UserMapper.mapToUserDto(getUserByEmailOrUsername);

        String token = jwtService.genrerateToken(userDto, null, authentication);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(token);
        Map<String, TokenResponse> response = new HashMap<>();
        response.put("success", tokenResponse);

        return ResponseEntity.ok(response);
    }

    public UserDto findUserByMail (String userMail) {
        UserEntity userEntity = authRepository.findByEmail(userMail).get();
        return UserMapper.mapToUserDto(userEntity);
    }

    public ResponseEntity<?> findUserById (Integer userId) {
        UserEntity userEntity = authRepository.findById(userId).orElse(null);
        if (userEntity == null) {
            response.put("error", "Invalid credentials !");
            return ResponseEntity.badRequest().body(response);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity authentitcateUser = authRepository.findByEmail(authentication.getName()).orElse(null);
        
        if (authentitcateUser.getId().equals(userId) == false) {
            response.put("error", "Invalid user");
            return ResponseEntity.badRequest().body(response);
        }

        UserDto userDto = UserMapper.mapToUserDto(userEntity);
        return ResponseEntity.ok(userDto);
    }
}
