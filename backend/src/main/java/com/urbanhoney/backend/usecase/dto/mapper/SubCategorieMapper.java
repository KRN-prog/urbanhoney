package com.urbanhoney.backend.usecase.dto.mapper;

import com.urbanhoney.backend.models.SubCategorieEntity;
import com.urbanhoney.backend.usecase.dto.SubCategorieDto;
import com.urbanhoney.backend.usecase.dto.request.AddSubCategorieRequestDto;

public class SubCategorieMapper {
    public static SubCategorieEntity mapToSubCategorieEntity(AddSubCategorieRequestDto addSubCategorieRequestDto) {
        return new SubCategorieEntity(
            null,
            addSubCategorieRequestDto.getSubCategorieName(),
            addSubCategorieRequestDto.getCategorieLinkId(),
            addSubCategorieRequestDto.getGender());
    }

    public static SubCategorieDto mapToSubCategorieDto(SubCategorieEntity subCategorieEntity) {
        return new SubCategorieDto(
            subCategorieEntity.getSubCategorieId(),
            subCategorieEntity.getSubCategorieName(),
            subCategorieEntity.getCategorie(),
            subCategorieEntity.getGender());
    }
}
