package com.urbanhoney.backend.usecase.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetArticlesByGenderDto {
    
    @NotEmpty(message = "please enter a gender for the categorie")
    @Pattern(regexp = "^(H|F|H/F)$", message = "Gender must be 'H', 'F', or 'H/F'")
    @JsonProperty("gender")
    private String gender;

}
