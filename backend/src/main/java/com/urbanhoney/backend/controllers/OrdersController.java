package com.urbanhoney.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urbanhoney.backend.services.OrdersService;
import com.urbanhoney.backend.usecase.dto.request.AddOrderRequestDto;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/urbanhoney")
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    @PostMapping("/order/new")
    public ResponseEntity<?> newOrder(@Valid @RequestBody AddOrderRequestDto addOrderRequestDto) {
        return ordersService.postNewOrder(addOrderRequestDto);
    }
    
    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrdersOfUser(Authentication authentication) {
        return ordersService.getOrdersFromUser(authentication);
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<?> deleteOrderById(@PathVariable("orderId") Integer orderId) {
        return ordersService.deleteOrderById(orderId);
    }

}
