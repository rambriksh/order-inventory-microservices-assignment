package com.korber.Order.service;

import com.korber.Order.config.Configuration;
import com.korber.Order.dto.InventoryBatch;
import com.korber.Order.dto.OrderRequest;
import com.korber.Order.dto.Product;
import com.korber.Order.model.Orders;
import com.korber.Order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Configuration configuration;

    @Test
    void shouldPlaceOrderWhenInventoryIsSufficient() {
        OrderRequest request = new OrderRequest(1L, 5);
        Product product=new Product();
        InventoryBatch batch = new InventoryBatch();
        batch.setId(1L);
        batch.setProduct(product);
        batch.setQuantity(10);
        batch.setExpiryDate(LocalDate.from(LocalDateTime.now().plusDays(30)));

        List<InventoryBatch> batchList = List.of(batch);
        ResponseEntity<List<InventoryBatch>> responseEntity = ResponseEntity.ok(batchList);

        when(configuration.getInventoryServiceUrl()).thenReturn("http://localhost:8081/inventory/");
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class))
        ).thenReturn(responseEntity);
//        when(restTemplate.exchange(
//                eq("http://localhost:8081/inventory/P001"),
//                eq(HttpMethod.GET),
//                isNull(),
//                any(ParameterizedTypeReference.class))
//        ).thenReturn(responseEntity);

        when(orderRepository.save(any(Orders.class))).thenAnswer(inv -> inv.getArgument(0));

//        when(restTemplate.exchange(
//                eq("http://localhost:8081/inventory/update"),
//                eq(HttpMethod.POST),
//                any(HttpEntity.class),
//                eq(String.class))
//        ).thenReturn(ResponseEntity.ok("Updated"));

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(String.class))
        ).thenReturn(ResponseEntity.ok("Updated"));

        Orders result = orderService.placeOrder(request);

        assertEquals(1, result.getProductId());
        assertEquals(5, result.getQuantity());
        assertNotNull(result.getOrderDate());
    }

    @Test
    void shouldThrowExceptionWhenInventoryIsNull() {
        OrderRequest request = new OrderRequest(2L, 3);

        ResponseEntity<List<InventoryBatch>> responseEntity = ResponseEntity.ok(null);

        when(configuration.getInventoryServiceUrl()).thenReturn("http://localhost:8081/inventory/");
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class))
        ).thenReturn(responseEntity);

        assertThrows(RuntimeException.class, () -> orderService.placeOrder(request));
    }

    @Test
    void shouldThrowExceptionWhenInventoryIsInsufficient() {
        OrderRequest request = new OrderRequest(1L, 10);

        InventoryBatch batch = new InventoryBatch();
        Product product=new Product();
        batch.setId(2L);
        batch .setProduct(product);
        batch.setQuantity(5); // insufficient
        batch.setExpiryDate(LocalDate.from(LocalDateTime.now().plusDays(30)));

        List<InventoryBatch> batchList = List.of(batch);
        ResponseEntity<List<InventoryBatch>> responseEntity = ResponseEntity.ok(batchList);

        when(configuration.getInventoryServiceUrl()).thenReturn("http://localhost:8081/inventory/");
        when(restTemplate.exchange(
                eq("http://localhost:8081/inventory/P003"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class))
        ).thenReturn(responseEntity);

        when(orderRepository.save(any(Orders.class))).thenAnswer(inv -> inv.getArgument(0));

        when(restTemplate.exchange(
                eq("http://localhost:8081/inventory//update"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(String.class))
        ).thenReturn(ResponseEntity.ok("Updated"));

        assertThrows(RuntimeException.class, () -> orderService.placeOrder(request));
    }
}
