package com.urbanhoney.backend.usecase.dto.mapper;

import com.urbanhoney.backend.models.OrdersEntity;
import com.urbanhoney.backend.usecase.dto.request.AddOrderRequestDto;

public class OrderMapper {

    public static OrdersEntity mapToOrdersEntity(AddOrderRequestDto addOrderRequestDto) {
        return new OrdersEntity(
            null,
            addOrderRequestDto.getArticleList(),
            addOrderRequestDto.getTotal(),
            addOrderRequestDto.getUserId());
    }

}
