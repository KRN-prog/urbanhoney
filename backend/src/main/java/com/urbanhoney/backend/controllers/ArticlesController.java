package com.urbanhoney.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urbanhoney.backend.services.ArticlesService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/urbanhoney")
public class ArticlesController {

    @Autowired
    private ArticlesService articlesService;
    
    @GetMapping("/article/{articleId}")
    public String getArticle() {
        return "Get one article";
    }

    /*@GetMapping("/articles/{articlesType}")
    public List<String> getArticlesByType(@PathVariable("articlesType") String articleType) {
        return articlesService.findArticlesByType(articleType);
    }

    @GetMapping("/articles")
    public List<String> getAllArticles() {
        return "Get all articles";
    }*/
    
    @PostMapping("/article")
    public String postNewArticle() {
        return "Post a new article";
    }
    
}
