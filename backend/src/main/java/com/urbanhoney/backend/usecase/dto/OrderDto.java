package com.urbanhoney.backend.usecase.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.urbanhoney.backend.models.ArticlesEntity;
import com.urbanhoney.backend.models.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    
    private Integer orderId;

    @JsonProperty("article_id")
    private List<ArticlesEntity> articlesList;

    @JsonProperty("total")
    private String total;

    @JsonProperty("user_id")
    private UserEntity userId;
}
