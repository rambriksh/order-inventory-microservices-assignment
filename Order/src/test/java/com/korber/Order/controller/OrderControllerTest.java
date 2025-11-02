package com.korber.Order.controller;

import com.korber.Order.dto.OrderRequest;
import com.korber.Order.model.Orders;
import com.korber.Order.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Test
    void shouldPlaceOrderSuccessfully() throws Exception {
        OrderRequest request = new OrderRequest(1L, 3);

        Orders mockOrder = new Orders();
        mockOrder.setId(1L);
        mockOrder.setProductId(1L);
        mockOrder.setQuantity(3);
        mockOrder.setOrderDate(LocalDateTime.now());

        Mockito.when(orderService.placeOrder(Mockito.any(OrderRequest.class))).thenReturn(mockOrder);
        ResponseEntity<Orders> response = orderController.placeOrder(request);
        Assertions.assertEquals(1, response.getBody().getProductId());
        Assertions.assertEquals(3, response.getBody().getQuantity());

    }
}
