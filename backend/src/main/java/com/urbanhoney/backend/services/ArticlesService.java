package com.urbanhoney.backend.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.urbanhoney.backend.models.ArticlesEntity;
import com.urbanhoney.backend.repository.ArticlesRepository;

@Service
public class ArticlesService {

    private ArticlesRepository articlesRepository;

    public ArticlesService(ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }

    public ResponseEntity<?> findArticlesById(Integer article_id) {
        List<ArticlesEntity> articlesList = articlesRepository.findAllByArticle_id(article_id);

        if (articlesList == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Cannout find any article");
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok("OK");
    }
    /*List<ThemeDto> themesDtos = theme.stream()
                .map(ThemeMapper::maptoThemesDto)
                .collect(Collectors.toList());*/
}
