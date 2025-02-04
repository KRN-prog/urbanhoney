package com.urbanhoney.backend.usecase.dto.mapper;

import com.urbanhoney.backend.models.CategorieEntity;
import com.urbanhoney.backend.usecase.dto.CategorieDto;
import com.urbanhoney.backend.usecase.dto.request.AddCategorieRequestDto;

public class CategorieMapper {
    public static CategorieEntity mapToCategorieEntity(AddCategorieRequestDto addCategorieRequestDto) {
        return new CategorieEntity(
            null,
            addCategorieRequestDto.getCategorie(),
            addCategorieRequestDto.getGender(),
            addCategorieRequestDto.getCategory_picture());
    }

    public static CategorieDto mapToCategorieDto(CategorieEntity categorieEntity) {
        return new CategorieDto(
            categorieEntity.getCategorieId(),
            categorieEntity.getCategorie(),
            categorieEntity.getGender());
    }
}
