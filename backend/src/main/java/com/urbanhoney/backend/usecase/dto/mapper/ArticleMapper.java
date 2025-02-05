package com.urbanhoney.backend.usecase.dto.mapper;

import java.util.ArrayList;

import com.urbanhoney.backend.models.ArticlesEntity;
import com.urbanhoney.backend.usecase.dto.ArticleDto;
import com.urbanhoney.backend.usecase.dto.request.AddArticleRequestDto;

public class ArticleMapper {
    public static ArticlesEntity mapToArticleEntity(AddArticleRequestDto addArticleRequestDto) {
        return new ArticlesEntity(
            null,
            addArticleRequestDto.getTitle(),
            addArticleRequestDto.getDescription(),
            addArticleRequestDto.getSubCategorie(),
            addArticleRequestDto.getBrand(),
            addArticleRequestDto.getPrice(),
            addArticleRequestDto.getColor(),
            addArticleRequestDto.getSize(),
            addArticleRequestDto.getPictures(),
            addArticleRequestDto.getComposition(),
            addArticleRequestDto.getEntretien(),
            new ArrayList<>());
    }


    public static ArticleDto mapToArticleDto(ArticlesEntity articlesEntity) {
        return new ArticleDto (
            articlesEntity.getArticleId(),
            articlesEntity.getTitle(),
            articlesEntity.getDescription(),
            articlesEntity.getSubCategoryLinkedId(),
            articlesEntity.getBrand(),
            articlesEntity.getPrice(),
            articlesEntity.getColor(),
            articlesEntity.getSize(),
            articlesEntity.getPictures(),
            articlesEntity.getComposition(),
            articlesEntity.getEntretien());
    }
}
