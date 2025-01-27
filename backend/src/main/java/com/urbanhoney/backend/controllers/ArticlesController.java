package com.urbanhoney.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urbanhoney.backend.services.ArticlesService;
import com.urbanhoney.backend.usecase.dto.request.AddArticleRequestDto;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/urbanhoney")
public class ArticlesController {

    @Autowired
    private ArticlesService articlesService;

    @PostMapping("/article")
    public ResponseEntity<?> postNewArticle(@Valid @RequestBody AddArticleRequestDto addArticleRequest) {
        return articlesService.addNewArticle(addArticleRequest);
    }
    
    @GetMapping("/article/{articleId}")
    public ResponseEntity<?> getArticleById(@PathVariable("articleId") Integer articleId) {
        return articlesService.getArticleById(articleId);
    }

    @GetMapping("/articles")
    public ResponseEntity<?> getAllArticles() {
        return articlesService.getAllArticles();
    }

    @GetMapping("/articles/{articlesType}")
    public ResponseEntity<?> getArticlesByType(@PathVariable("articlesType") String articleType) {
        return articlesService.getArticleByType(articleType);
    }

    @DeleteMapping()
    public String deleteArticleById(@PathVariable("articlesType") Integer articleId) {
        return "Delete article by id";
    }
    
}
