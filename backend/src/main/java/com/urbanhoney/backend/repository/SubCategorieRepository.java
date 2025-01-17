package com.urbanhoney.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urbanhoney.backend.models.SubCategorieEntity;
import com.urbanhoney.backend.usecase.dto.request.AddSubCategorieRequestDto;

@Repository
public interface SubCategorieRepository extends JpaRepository<SubCategorieEntity, Long> {
    void save(AddSubCategorieRequestDto addSubCategorieRequestDto);
}
