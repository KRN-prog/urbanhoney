package com.urbanhoney.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urbanhoney.backend.models.OrdersEntity;
import com.urbanhoney.backend.usecase.dto.request.AddOrderRequestDto;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {
    void save(AddOrderRequestDto addOrderRequestDto);
}
