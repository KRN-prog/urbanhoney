package com.urbanhoney.backend.usecase.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class AuthRequestDto {
    @NotEmpty(message = "Please enter an email or a username")
    private String emailOrUsername;

    @NotEmpty(message = "Please enter your password")
    @Min(value = 8, message = "Your password must be 8 char minimum")
    private String password;
}
