package com.urbanhoney.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urbanhoney.backend.models.SubCategorieEntity;
import com.urbanhoney.backend.usecase.dto.request.AddSubCategorieRequestDto;


@Repository
public interface SubCategorieRepository extends JpaRepository<SubCategorieEntity, Long> {

    List<SubCategorieEntity> findAll();

    Optional<SubCategorieEntity> findBySubCategorieId(Integer subCategorieId);

    Optional<SubCategorieEntity> findBySubCategorieName(String subCategorieName);

    void deleteBySubCategorieName(String subCategorieName);

    void save(AddSubCategorieRequestDto addSubCategorieRequestDto);
}
