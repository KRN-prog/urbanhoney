package com.urbanhoney.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urbanhoney.backend.models.ArticlesEntity;
import com.urbanhoney.backend.usecase.dto.ArticleDto;

@Repository
public interface ArticlesRepository extends JpaRepository<ArticlesEntity, Long> {

    @SuppressWarnings("null")
    List<ArticlesEntity> findAll();

    Optional<ArticlesEntity> findByArticleId(Integer articleId);

    List<ArticlesEntity> findBySubCategoryLinkedId_Categorie_CategoryName(String articleType);

    void save(ArticleDto articleDto);
}
