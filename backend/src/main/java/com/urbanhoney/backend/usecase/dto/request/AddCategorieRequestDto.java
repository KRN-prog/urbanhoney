package com.urbanhoney.backend.usecase.dto.request;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCategorieRequestDto {
    @NotEmpty(message = "Please enter a category name")
    @Size(max = 15, message = "Your category name must not excede 15 characters")
    @JsonProperty("category_name")
    private String categoryName;

    @NotEmpty(message = "please enter a gender for the categorie")
    @Pattern(regexp = "^(H|F|H/F)$", message = "Gender must be 'H', 'F', or 'H/F'")
    private String gender;

    @NotEmpty(message = "Please enter a picture link for your categorie")
    @URL(message = "The link is not valide")
    @JsonProperty("category_picture")
    private String categoryPicture;
}
