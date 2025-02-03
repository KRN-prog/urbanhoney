package com.urbanhoney.backend.usecase.dto.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.urbanhoney.backend.models.ArticlesEntity;
import com.urbanhoney.backend.models.UserEntity;
import com.urbanhoney.backend.usecase.dto.ArticleDto;
import com.urbanhoney.backend.usecase.dto.UserDto;

import jakarta.validation.constraints.NotEmpty;
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
    private List<ArticlesEntity> articleList = new ArrayList<>();
    
    @NotNull(message = "Your order must have a total price")
    private List<String> total;
    private UserEntity userId;
}
