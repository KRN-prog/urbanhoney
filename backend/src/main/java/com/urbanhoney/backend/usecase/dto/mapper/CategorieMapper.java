package com.urbanhoney.backend.usecase.dto.mapper;

import com.urbanhoney.backend.models.CategorieEntity;
import com.urbanhoney.backend.usecase.dto.request.AddCategorieRequestDto;

public class CategorieMapper {
    public static CategorieEntity mapToUserDto(AddCategorieRequestDto addCategorieRequestDto) {
        return new CategorieEntity(
            null,
            addCategorieRequestDto.getCategorie(),
            addCategorieRequestDto.getGender());
    }
}
