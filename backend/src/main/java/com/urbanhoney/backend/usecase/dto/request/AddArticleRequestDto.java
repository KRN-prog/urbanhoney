package com.urbanhoney.backend.usecase.dto.request;

import com.urbanhoney.backend.models.SubCategorieEntity;

import lombok.Getter;

@Getter
public class AddArticleRequestDto {
    private String title;
    private String description;
    private SubCategorieEntity subCategorie;
    private String brand;
    private String price;
    private String color;
    private String size;
    private String pictures;
    private String composition;
    private String entretien;
}
