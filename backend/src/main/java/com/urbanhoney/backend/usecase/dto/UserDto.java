package com.urbanhoney.backend.usecase.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.urbanhoney.backend.models.OrdersEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("profile_picture")
    private String profilePicture;

    @JsonProperty("is_admin")
    private Boolean is_admin;
}
