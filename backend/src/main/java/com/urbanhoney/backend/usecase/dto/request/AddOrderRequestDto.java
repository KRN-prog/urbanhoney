package com.urbanhoney.backend.usecase.dto.request;

import java.util.List;

import com.urbanhoney.backend.models.ArticlesEntity;
import com.urbanhoney.backend.models.UserEntity;

import lombok.Getter;

@Getter
public class AddOrderRequestDto {
    private List<ArticlesEntity> ArticeList;
    private String total;
    private UserEntity userId;
}
