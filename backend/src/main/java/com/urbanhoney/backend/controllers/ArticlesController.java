package com.urbanhoney.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/urbanhoney")
public class ArticlesController {
    
    @GetMapping("/article/{articleId}")
    public String getArticle() {
        return "Get one article";
    }

    @GetMapping("/articles/{articlesType}")
    public String getArticlesByType() {
        return "Get articles by type";
    }

    @GetMapping("/articles")
    public String getAllArticles() {
        return "Get all articles";
    }
    
    @PostMapping("/article")
    public String postNewArticle() {
        return "Post a new article";
    }
    
}
