package com.urbanhoney.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urbanhoney.backend.services.OrdersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/urbanhoney")
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    @PostMapping("/order/new")
    public String newOrder(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    
    
    @GetMapping("/orders")
    public String getAllOrdersOfUser(Authentication authentication) {
        return ordersService.getAllOrders(authentication);
    }
    
}
