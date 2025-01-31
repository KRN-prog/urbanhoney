package com.urbanhoney.backend.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.urbanhoney.backend.models.OrdersEntity;
import com.urbanhoney.backend.models.UserEntity;
import com.urbanhoney.backend.repository.AuthRepository;
import com.urbanhoney.backend.repository.OrdersRepository;
import com.urbanhoney.backend.usecase.dto.mapper.OrderMapper;
import com.urbanhoney.backend.usecase.dto.request.AddOrderRequestDto;

import jakarta.transaction.Transactional;

@Service
public class OrdersService {

    private OrdersRepository ordersRepository;

    private AuthRepository authRepository;

    public OrdersService(OrdersRepository ordersRepository, AuthRepository authRepository) {
        this.ordersRepository = ordersRepository;
        this.authRepository = authRepository;
    }
    
    public ResponseEntity<?> postNewOrder(AddOrderRequestDto addOrderRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity authentitcateUser = authRepository.findByEmail(authentication.getName()).orElse(null);

        if (authentitcateUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found !"));
        }

        System.out.println(addOrderRequestDto.getArticleList());
        System.out.println(addOrderRequestDto.getTotal());
        System.out.println(addOrderRequestDto.getUserId());
        if (addOrderRequestDto.getArticleList().isEmpty() || addOrderRequestDto.getTotal().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Please fill all fields !"));
        }

        OrdersEntity ordersEntity = OrderMapper.mapToOrdersEntity(addOrderRequestDto);

        ordersRepository.save(ordersEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success", "Order taken !"));
    }

    public ResponseEntity<?> getOrdersFromUser(Authentication authentication) {
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
    public ResponseEntity<?> deleteOrderById(Integer orderId) {
        if (ordersRepository.existsByOrderId(orderId) == false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Order not found !"));
        }
        ordersRepository.deleteByOrderId(orderId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("success", "Order deleted !"));
    }
}
