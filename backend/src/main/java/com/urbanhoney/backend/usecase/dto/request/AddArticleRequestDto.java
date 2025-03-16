package com.urbanhoney.backend.usecase.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.urbanhoney.backend.converter.StringListConverter;
import com.urbanhoney.backend.models.SubCategorieEntity;

import jakarta.persistence.Convert;
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

    @JsonProperty("sub_category")
    private SubCategorieEntity subCategorie;

    @NotEmpty(message = "The brand field can't be empty")
    private String brand;

    @Convert(converter = StringListConverter.class)
    @NotEmpty(message = "You need to set a price on your article")
    private List<String> price;

    @Convert(converter = StringListConverter.class)
    @NotEmpty(message = "You need to set at least one color on your article")
    @Size(min = 1, message = "You need to set at least one color on your article")
    private List<@Pattern(
        regexp = "^#[a-fA-F0-9]{6}$",
        message = "Invalid color format. Each color must be in HEX format, e.g., #123abc or #456DEF"
    ) String> color;

    @Convert(converter = StringListConverter.class)
    @NotEmpty(message = "You need to set at least one size on your article")
    @Size(min = 1, message = "You need to set at least one size on your article")
    private List<@Pattern(
        regexp = "XS|S|M|L|XL|XXL",
        message = "Invalid size. Allowed values: XS, S, M, L, XL, XXL"
    ) String> size;

    @Convert(converter = StringListConverter.class)
    @NotEmpty(message = "Please upload at least one image of an article")
    private List<String> pictures;

    @NotEmpty(message = "Please enter the composition of the article")
    private String composition;

    @NotEmpty(message = "Please enter the entretien of the article")
    private String entretien;

    @NotEmpty(message = "Please enter a valid gender")
    private String gender;
}
