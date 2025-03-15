package com.urbanhoney.backend.usecase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.urbanhoney.backend.models.CategorieEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubCategorieDto {
    
    @JsonProperty("sub_category_id")
    private Integer subCategorieId;

    @JsonProperty("sub_category_name")
    private String subCategorieName;

    @JsonProperty("category_linked_id")
    private CategorieEntity categorie;

    @JsonProperty("gender")
    private String gender;
}
