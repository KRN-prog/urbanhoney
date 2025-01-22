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
        Map<String, String> response = new HashMap<>();
        SubCategorieEntity subCategorie = subCategorieRepository.findBySubCategorieId(addArticleRequest.getSubCategorie().getSubCategorieId()).orElse(null);

        if (subCategorie == null) {
            response.put("error", "Sub categorie linked to the article not found !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ArticlesEntity articlesEntity = ArticleMapper.mapToArticleEntity(addArticleRequest);
        articlesRepository.save(articlesEntity);
        response.put("success", "Article saved !");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<?> getArticleById(Integer articleId) {
        ArticlesEntity articlesEntity = articlesRepository.findByArticleId(articleId).orElse(null);

        if (articlesEntity == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Cannout find any article.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Map<String, ArticleDto> response = new HashMap<>();
        ArticleDto articleDto = ArticleMapper.mapToArticleDto(articlesEntity);
        response.put("success", articleDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<?> getAllArticles() {
        List<ArticlesEntity> articlesEntities = articlesRepository.findAll();
        if (articlesEntities.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "No articles found !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Map<String, List<ArticlesEntity>> response = new HashMap<>();
        response.put("success", articlesEntities);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<?> getArticleByType(String articleType) {

        List<ArticlesEntity> articleEntity = articlesRepository.findBySubCategorieLinkedId_SubCategorieName(articleType);
        if (articleEntity == null || articleEntity.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "No sub articles found !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        List<ArticleDto> articleDtos = articleEntity.stream()
                .map(ArticleMapper::mapToArticleDto)
                .collect(Collectors.toList());

        Map<String, List<ArticleDto>> response = new HashMap<>();
        response.put("success", articleDtos);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
