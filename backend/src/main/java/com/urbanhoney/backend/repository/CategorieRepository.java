package com.urbanhoney.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urbanhoney.backend.models.CategorieEntity;
import com.urbanhoney.backend.usecase.dto.request.AddCategorieRequestDto;

@Repository
public interface CategorieRepository extends JpaRepository<CategorieEntity, Long> {

    boolean existsByCategoryId(Integer categorieId);

    @SuppressWarnings("null")
    List<CategorieEntity> findAll();

    Optional<CategorieEntity> findByCategoryName(String categoryName);

    void save(AddCategorieRequestDto addCategorieRequestDto);

}
