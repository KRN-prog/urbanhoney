package com.urbanhoney.backend.usecase.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCategorieRequestDto {
    @NotEmpty(message = "Please enter a categorie name")
    @Size(max = 15, message = "Your categorie name must not excede 15 characters")
    private String categorie;

    @NotEmpty(message = "please enter a gender for the categorie")
    @Pattern(regexp = "^(H|F|H/F)$", message = "Gender must be 'H', 'F', or 'H/F'")
    private String gender;
}
