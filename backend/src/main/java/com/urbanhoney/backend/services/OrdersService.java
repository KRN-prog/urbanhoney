package com.urbanhoney.backend.services;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {
    
    public String getAllOrders(Authentication authentication) {
        return "ULTRA SEXY DOULLABIT";
    }
}
