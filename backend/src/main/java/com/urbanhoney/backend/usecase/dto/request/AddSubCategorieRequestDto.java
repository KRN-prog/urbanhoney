package com.urbanhoney.backend.usecase.dto.request;

import com.urbanhoney.backend.models.CategorieEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddSubCategorieRequestDto {
    private String subCategorieName;
    private CategorieEntity categorieLinkId;
    private String gender;
}
