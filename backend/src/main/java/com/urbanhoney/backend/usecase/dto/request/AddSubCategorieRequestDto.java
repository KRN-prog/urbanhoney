package com.urbanhoney.backend.usecase.dto.request;

import com.urbanhoney.backend.models.CategorieEntity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddSubCategorieRequestDto {

    @NotEmpty(message = "Please enter a sub categorie name")
    @Max(value = 15, message = "Your sub categorie name must not excede 15 characters")
    private String subCategorieName;

    private CategorieEntity categorieLinkId;

    @NotEmpty(message = "please enter a gender for the categorie")
    @Pattern(regexp = "^(H|F|H/F)$", message = "Gender must be 'H', 'F', or 'H/F'")
    private String gender;
}
