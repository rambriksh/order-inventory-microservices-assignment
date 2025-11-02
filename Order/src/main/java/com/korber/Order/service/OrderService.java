package com.korber.Order.service;

import com.korber.Order.dto.OrderRequest;
import com.korber.Order.model.Orders;

public interface OrderService {
    Orders placeOrder(OrderRequest request);
}
