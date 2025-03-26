package com.urbanhoney.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.urbanhoney.backend.models.ArticlesEntity;
import com.urbanhoney.backend.models.OrderArticleEntity;
import com.urbanhoney.backend.models.OrdersEntity;
import com.urbanhoney.backend.models.UserEntity;
import com.urbanhoney.backend.repository.ArticlesRepository;
import com.urbanhoney.backend.repository.AuthRepository;
import com.urbanhoney.backend.repository.OrdersRepository;
import com.urbanhoney.backend.usecase.dto.request.AddOrderRequestDto;

import jakarta.transaction.Transactional;

@Service
public class OrdersService {

    private OrdersRepository ordersRepository;

    private AuthRepository authRepository;

    private ArticlesRepository articlesRepository;

    public OrdersService(OrdersRepository ordersRepository, AuthRepository authRepository, ArticlesRepository articlesRepository) {
        this.ordersRepository = ordersRepository;
        this.authRepository = authRepository;
        this.articlesRepository = articlesRepository;
    }
    
    @Transactional
    public ResponseEntity<Map<String, String>> postNewOrder(AddOrderRequestDto addOrderRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity authentitcateUser = authRepository.findByEmail(authentication.getName()).orElse(null);

        if (authentitcateUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found !"));
        }
        
        if (addOrderRequestDto.getArticleList().isEmpty() || addOrderRequestDto.getTotal().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Please fill all fields !"));
        }

        OrdersEntity order = new OrdersEntity();

        UserEntity user = authRepository.findById(addOrderRequestDto.getUserId().getId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        order.setUserId(user);
        order.setTotal(addOrderRequestDto.getTotal());

        List<ArticlesEntity> articles = new ArrayList<>();
        for (Integer articleId : addOrderRequestDto.getArticleList()) {
            ArticlesEntity managedArticle = articlesRepository.findByArticleId(articleId)
                    .orElseThrow(() -> new RuntimeException("Article not found"));

            articles.add(managedArticle);
        }

        List<OrderArticleEntity> articlesOrdered = new ArrayList<>();
        for(int j = 0; j < articles.size(); j++) {
            OrderArticleEntity orderedArticle = new OrderArticleEntity();
            orderedArticle.setOrder(order);
            orderedArticle.setArticles(articles.get(j));
            orderedArticle.setQuantity(addOrderRequestDto.getColor().size());
            orderedArticle.setSize(addOrderRequestDto.getSize().get(j));
            orderedArticle.setColor(addOrderRequestDto.getColor().get(j));
            articlesOrdered.add(orderedArticle);
        }

        order.setOrderArticles(articlesOrdered);
        ordersRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success", "Order taken !"));
    }

    public ResponseEntity<Object> getOrdersFromUser(Authentication authentication) {
        UserEntity authentitcateUser = authRepository.findByEmail(authentication.getName()).orElse(null);

        if (authentitcateUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found !"));
        }
        
        List<OrdersEntity> ordersEntities = ordersRepository.findAllByUserId_Id(authentitcateUser.getId());

        if (ordersEntities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No order found !"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(ordersEntities);
    }

    @Transactional
    public ResponseEntity<Map<String, String>> deleteOrderById(Integer orderId) {
        if (ordersRepository.existsByOrderId(orderId) == false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Order not found !"));
        }
        ordersRepository.deleteByOrderId(orderId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("success", "Order deleted !"));
    }
}
