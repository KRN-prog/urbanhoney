package com.urbanhoney.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urbanhoney.backend.services.OrdersService;
import com.urbanhoney.backend.usecase.dto.request.AddOrderRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/urbanhoney")
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    @PostMapping("/order/new")
    public ResponseEntity<?> newOrder(@RequestBody AddOrderRequestDto addOrderRequestDto) {
        return ordersService.postNewOrder(addOrderRequestDto);
    }
    
}
