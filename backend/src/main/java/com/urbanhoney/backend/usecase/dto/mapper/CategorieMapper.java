package com.urbanhoney.backend.usecase.dto.mapper;

import com.urbanhoney.backend.models.CategorieEntity;
import com.urbanhoney.backend.usecase.dto.CategorieDto;
import com.urbanhoney.backend.usecase.dto.request.AddCategorieRequestDto;

public class CategorieMapper {
    public static CategorieEntity mapToCategorieEntity(AddCategorieRequestDto addCategorieRequestDto) {
        return new CategorieEntity(
            null,
            addCategorieRequestDto.getCategoryName(),
            addCategorieRequestDto.getGender(),
            addCategorieRequestDto.getCategoryName());
    }

    public static CategorieDto mapToCategorieDto(CategorieEntity categorieEntity) {
        return new CategorieDto(
            categorieEntity.getCategoryId(),
            categorieEntity.getCategoryName(),
            categorieEntity.getGender(),
            categorieEntity.getCategoryPicture());
    }
}
