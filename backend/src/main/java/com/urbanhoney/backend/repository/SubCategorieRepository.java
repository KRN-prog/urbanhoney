package com.urbanhoney.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urbanhoney.backend.models.SubCategorieEntity;
import com.urbanhoney.backend.usecase.dto.request.AddSubCategorieRequestDto;


@Repository
public interface SubCategorieRepository extends JpaRepository<SubCategorieEntity, Long> {

    @SuppressWarnings("null")
    List<SubCategorieEntity> findAll();

    Optional<SubCategorieEntity> findBySubcategoryId(Integer subcategoryId);

    Optional<SubCategorieEntity> findBySubCategoryName(String subCategoryName);

    Optional<SubCategorieEntity> findByCategorie_CategoryName(String categoryName);

    void deleteBySubCategoryName(String subCategoryName);

    void save(AddSubCategorieRequestDto addSubCategorieRequestDto);
}
