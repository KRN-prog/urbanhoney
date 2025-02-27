package com.urbanhoney.backend.usecase.dto.request;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.urbanhoney.backend.models.ArticlesEntity;
import com.urbanhoney.backend.models.UserEntity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddOrderRequestDto {
    @JsonProperty("article_list")
    private List<Integer> articleList = new ArrayList<>();
    
    @NotNull(message = "Your order must have a total price")
    private String total;
    private UserEntity userId;
}
