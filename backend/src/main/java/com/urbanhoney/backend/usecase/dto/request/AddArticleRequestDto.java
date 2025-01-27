package com.urbanhoney.backend.usecase.dto.request;

import com.urbanhoney.backend.models.SubCategorieEntity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AddArticleRequestDto {

    @NotEmpty(message = "The title of an article can't be empty.")
    @Size(max = 35, message = "The title can't excede 35 characters.")
    private String title;

    @NotEmpty(message = "The description of a title can't be empty")
    @Size(max = 200, message = "The description of an article can't excede 200 characters.")
    private String description;

    @NotEmpty(message = "This field can't be empty.")
    private SubCategorieEntity subCategorie;

    @NotEmpty(message = "The brand field can't be empty")
    private String brand;

    @NotEmpty(message = "You need to set a price on your article")
    private String price;

    @NotEmpty(message = "You need to set at least one color on your article")
    @Pattern(
        regexp = "\\[[#][a-fA-F0-9]{6}(?:,#[a-fA-F0-9]{6})*\\]",
        message = "Invalid format. Expected format: [#123abc,#456DEF]"
    )
    private String color;

    @NotEmpty(message = "You need to set at least one size on your article")
    @Pattern(
        regexp = "^\\[(XS|S|M|L|XL|XXL)(,(XS|S|M|L|XL|XXL))*\\]$",
        message = "Invalid sizes format. Expected format: [XS], [XS,S,M,L], etc."
    )
    private String size;

    @NotEmpty(message = "Please upload at least one image of an article")
    private String pictures;

    @NotEmpty(message = "Please enter the composition of the article")
    private String composition;

    @NotEmpty(message = "Please enter the entretien of the article")
    private String entretien;
}
