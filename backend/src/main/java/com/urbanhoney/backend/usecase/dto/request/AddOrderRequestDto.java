package com.urbanhoney.backend.usecase.dto.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.urbanhoney.backend.models.ArticlesEntity;
import com.urbanhoney.backend.models.UserEntity;

import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Your order must have a total price")
    private Map<Double, String> total;
    private UserEntity userId;
}
