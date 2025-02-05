package com.urbanhoney.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urbanhoney.backend.services.ArticlesService;
import com.urbanhoney.backend.services.IsAdminService;
import com.urbanhoney.backend.usecase.dto.request.AddArticleRequestDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    private final IsAdminService isAdminService;

    public ArticlesController(IsAdminService isAdminService) {
        this.isAdminService = isAdminService;
    }

    @PostMapping("/article")
    public ResponseEntity<?> postNewArticle(@Valid @RequestBody AddArticleRequestDto addArticleRequest, HttpServletRequest request) {
        if (!isAdminService.isAdmin(request)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "You don't have the permission to do that action"));
        }
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

    @DeleteMapping("/article/{articleId}")
    public ResponseEntity<?> deleteArticleById(@PathVariable("articlesType") Integer articleId, HttpServletRequest request) {
        if (!isAdminService.isAdmin(request)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "You don't have the permission to do that action"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", "Order deleted"));
    }
    
}
