package com.korber.Order.controller;

import com.korber.Order.dto.OrderRequest;
import com.korber.Order.model.Orders;
import com.korber.Order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Orders> placeOrder(@RequestBody OrderRequest request) {
        Orders order = orderService.placeOrder(request);
        return ResponseEntity.ok(order);
    }
}
