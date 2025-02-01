package com.urbanhoney.backend.usecase.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.urbanhoney.backend.models.SubCategorieEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Integer articleId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("sub_categorie_id")
    private SubCategorieEntity subCategorieId;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("price")
    private List<String> price;

    @JsonProperty("colors")
    private List<String> colors;

    @JsonProperty("size")
    private List<String> size;

    @JsonProperty("pictures")
    private List<String> pictures;

    @JsonProperty("composition")
    private String composition;

    @JsonProperty("entretient")
    private String entretient;
}
