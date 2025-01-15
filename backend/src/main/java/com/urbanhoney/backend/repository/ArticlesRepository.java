package com.urbanhoney.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urbanhoney.backend.models.ArticlesEntity;

@Repository
public interface ArticlesRepository extends JpaRepository<ArticlesEntity, Long> {
    List<ArticlesEntity> findAllBy(Integer article_id);
}
