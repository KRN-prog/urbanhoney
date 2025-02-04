package com.urbanhoney.backend.usecase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategorieDto {

    private Integer categoryId;

    @JsonProperty("category_name")
    private String category_name;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("category_picture")
    private String categoryPicture;
}
