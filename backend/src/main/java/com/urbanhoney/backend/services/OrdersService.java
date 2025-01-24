package com.urbanhoney.backend.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.urbanhoney.backend.models.UserEntity;
import com.urbanhoney.backend.repository.AuthRepository;
import com.urbanhoney.backend.repository.OrdersRepository;
import com.urbanhoney.backend.usecase.dto.request.AddOrderRequestDto;

@Service
public class OrdersService {

    private OrdersRepository ordersRepository;

    @Autowired
    private AuthRepository authRepository;

    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }
    
    public ResponseEntity<?> postNewOrder(AddOrderRequestDto addOrderRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity authentitcateUser = authRepository.findByEmail(authentication.getName()).orElse(null);

        if (authentitcateUser == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "User not found !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (addOrderRequestDto.getArticeList().isEmpty() || addOrderRequestDto.getTotal().isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Please fill all fields !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        System.out.println(addOrderRequestDto);

        ordersRepository.save(addOrderRequestDto);
        Map<String, String> response = new HashMap<>();
        response.put("success", "Order taken !");
        return ResponseEntity.status(HttpStatus.CREATED).body("response");
    }
}
