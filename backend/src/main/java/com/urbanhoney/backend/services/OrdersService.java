package com.urbanhoney.backend.services;

import java.util.HashMap;
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
            Map<String, String> response = new HashMap<>();
            response.put("error", "User not found !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (addOrderRequestDto.getArticleList().isEmpty() || addOrderRequestDto.getTotal().isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Please fill all fields !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        OrdersEntity ordersEntity = OrderMapper.mapToOrdersEntity(addOrderRequestDto);

        ordersRepository.save(ordersEntity);
        Map<String, String> response = new HashMap<>();
        response.put("success", "Order taken !");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<?> getOrdersFromUser(Authentication authentication) {
        UserEntity authentitcateUser = authRepository.findByEmail(authentication.getName()).orElse(null);

        if (authentitcateUser == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "User not found !");
        }
        
        List<OrdersEntity> ordersEntities = ordersRepository.findAllByUserId_Id(authentitcateUser.getId());

        if (ordersEntities.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "No order found !");
        }

        return ResponseEntity.status(HttpStatus.OK).body(ordersEntities);
    }

    @Transactional
    public ResponseEntity<?> deleteOrderById(Integer orderId) {
        if (ordersRepository.existsByOrderId(orderId) == false) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Order not found !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        ordersRepository.deleteByOrderId(orderId);

        Map<String, String> response = new HashMap<>();
        response.put("success", "Order deleted !");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
