package com.urbanhoney.backend.services;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
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
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public ResponseEntity<Map<String, String>> registerUser(UserDto userDto) {
        if (userDto.getEmail() != null || isValidEmail(userDto.getEmail()) == true) {
            UserEntity findByEmail = authRepository.findByEmail(userDto.getEmail()).orElse(null);
            UserEntity findByUsername = authRepository.findByUsername(userDto.getUsername()).orElse(null);
            if (findByEmail != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "This email is already taken !"));
            } else if (findByUsername != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "This username is already taken !"));
            } else {
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
                UserEntity userEntity = UserMapper.mapToUser(userDto);
                authRepository.save(userEntity);
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("Success", "User register successfully"));
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Please insert a real mail please."));
    }

    public ResponseEntity<Object> loginUser(AuthRequestDto authRequestDto, Authentication authentication) {
        UserEntity getUserByEmailOrUsername = authRepository.findByEmailOrUsername(authRequestDto.getEmailOrUsername()).orElse(null);
        if (getUserByEmailOrUsername == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Email or Username not found."));
        }

        if (passwordEncoder.matches(authRequestDto.getPassword(), getUserByEmailOrUsername.getPassword()) == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Your password is incorrect."));
        }

        UserDto userDto = UserMapper.mapToUserDto(getUserByEmailOrUsername);

        String token = jwtService.genrerateToken(userDto, null, authentication);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(token);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", tokenResponse));
    }

    public UserDto findUserByMail (String userMail) {
        UserEntity userEntity = authRepository.findByEmail(userMail).get();
        return UserMapper.mapToUserDto(userEntity);
    }

    public ResponseEntity<Object> findUserById (Integer userId) {
        UserEntity userEntity = authRepository.findById(userId).orElse(null);
        if (userEntity == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid credentials !"));
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity authentitcateUser = authRepository.findByEmail(authentication.getName()).orElse(null);
        
        if (authentitcateUser.getId().equals(userId) == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid user"));
        }

        UserDto userDto = UserMapper.mapToUserDto(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    public ResponseEntity<Object> getAllUsers(Authentication authentication) {
        UserEntity authentitcateUser = authRepository.findByEmail(authentication.getName()).orElse(null);

        if (authentitcateUser.getIsAdmin() == false) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "You must be an administator !"));
        }

        List<UserEntity> getAllUsers = authRepository.findAll();

        List<UserDto> userDtos = getAllUsers.stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("success", userDtos));
    }

    public ResponseEntity<Map<String, String>> revokeAdminStatus(Integer userId, Authentication authentication) {
        UserEntity authentitcateUser = authRepository.findByEmail(authentication.getName()).orElse(null);
        UserEntity getUser = authRepository.findById(userId).orElse(null);

        if (authentitcateUser.getIsAdmin() == false) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "You must be an administator !"));
        }

        if (getUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Your User ID is not valid !"));
        }

        getUser.setIsAdmin(false);
        authRepository.save(getUser);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("success", getUser.getUsername()+"Admin role as been revoked !"));
    }

    public ResponseEntity<Map<String, String>> setAdminStatus(Integer userId, Authentication authentication) {
        UserEntity authentitcateUser = authRepository.findByEmail(authentication.getName()).orElse(null);
        UserEntity getUser = authRepository.findById(userId).orElse(null);

        if (authentitcateUser.getIsAdmin() == false) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "You must be an administator !"));
        }

        if (getUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Your User ID is not valid !"));
        }

        getUser.setIsAdmin(true);
        authRepository.save(getUser);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("success", "Admin role as been set to"+ getUser.getUsername() +" !"));
    }
}
