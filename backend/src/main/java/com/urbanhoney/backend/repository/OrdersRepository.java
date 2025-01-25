package com.urbanhoney.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urbanhoney.backend.models.OrdersEntity;
import com.urbanhoney.backend.usecase.dto.request.AddOrderRequestDto;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {
    void save(AddOrderRequestDto addOrderRequestDto);

    List<OrdersEntity> findAllByUserId_Id(Integer userId);

    boolean existsByOrderId(Integer orderId);

    void deleteByOrderId(Integer orderId);
}
