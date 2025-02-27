package com.urbanhoney.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.urbanhoney.backend.models.ArticlesEntity;
import com.urbanhoney.backend.usecase.dto.ArticleDto;

@Repository
public interface ArticlesRepository extends JpaRepository<ArticlesEntity, Long> {

    @SuppressWarnings("null")
    List<ArticlesEntity> findAll();

    @Query("SELECT a FROM ArticlesEntity a WHERE a.articleId IN :articleIds")
    List<ArticlesEntity> findAllByArticleId(@Param("articleIds") List<Integer> articleIds);

    Optional<ArticlesEntity> findByArticleId(Integer articleId);

    List<ArticlesEntity> findBySubCategoryLinkedId_Categorie_CategoryName(String articleType);

    List<ArticlesEntity> findBySubCategoryLinkedId_SubCategoryName(String articleCategory);

    void save(ArticleDto articleDto);
}
