package com.urbanhoney.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urbanhoney.backend.models.CategorieEntity;
import com.urbanhoney.backend.usecase.dto.request.AddCategorieRequestDto;

@Repository
public interface CategorieRepository extends JpaRepository<CategorieEntity, Long> {

    Optional<CategorieEntity> findByCategorieId(Integer categorie_id);

    void save(AddCategorieRequestDto addCategorieRequestDto);

}
