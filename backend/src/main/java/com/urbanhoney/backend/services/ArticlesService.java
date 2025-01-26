package com.urbanhoney.backend.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.urbanhoney.backend.models.ArticlesEntity;
import com.urbanhoney.backend.models.SubCategorieEntity;
import com.urbanhoney.backend.repository.ArticlesRepository;
import com.urbanhoney.backend.repository.SubCategorieRepository;
import com.urbanhoney.backend.usecase.dto.ArticleDto;
import com.urbanhoney.backend.usecase.dto.mapper.ArticleMapper;
import com.urbanhoney.backend.usecase.dto.request.AddArticleRequestDto;

@Service
public class ArticlesService {

    private ArticlesRepository articlesRepository;

    @Autowired
    private SubCategorieRepository subCategorieRepository;

    public ArticlesService(ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }

    public ResponseEntity<?> addNewArticle(AddArticleRequestDto addArticleRequest) {
        SubCategorieEntity subCategorie = subCategorieRepository.findBySubCategorieId(addArticleRequest.getSubCategorie().getSubCategorieId()).orElse(null);

        if (subCategorie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Sub categorie linked to the article not found !"));
        }

        ArticlesEntity articlesEntity = ArticleMapper.mapToArticleEntity(addArticleRequest);
        articlesRepository.save(articlesEntity);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", "Article saved !"));
    }

    public ResponseEntity<?> getArticleById(Integer articleId) {
        ArticlesEntity articlesEntity = articlesRepository.findByArticleId(articleId).orElse(null);

        if (articlesEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cannout find any article."));
        }

        ArticleDto articleDto = ArticleMapper.mapToArticleDto(articlesEntity);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", articleDto));
    }

    public ResponseEntity<?> getAllArticles() {
        List<ArticlesEntity> articlesEntities = articlesRepository.findAll();
        if (articlesEntities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No articles found !"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", articlesEntities));
    }

    public ResponseEntity<?> getArticleByType(String articleType) {

        List<ArticlesEntity> articleEntity = articlesRepository.findBySubCategorieLinkedId_SubCategorieName(articleType);
        if (articleEntity == null || articleEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No sub articles found !"));
        }

        List<ArticleDto> articleDtos = articleEntity.stream()
                .map(ArticleMapper::mapToArticleDto)
                .collect(Collectors.toList());
                
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", articleDtos));
    }
}
