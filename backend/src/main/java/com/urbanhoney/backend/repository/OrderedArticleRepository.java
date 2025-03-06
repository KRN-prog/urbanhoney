package com.urbanhoney.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urbanhoney.backend.models.OrderArticleEntity;

@Repository
public interface OrderedArticleRepository extends JpaRepository <OrderArticleEntity, Long> {
    @SuppressWarnings("unchecked")
    OrderArticleEntity save(OrderArticleEntity orderArticleEntity);
}
