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

    @JsonProperty("category")
    private String category;

    @JsonProperty("gender")
    private String gender;
}
