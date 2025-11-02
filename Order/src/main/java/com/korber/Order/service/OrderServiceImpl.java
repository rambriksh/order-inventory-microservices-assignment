package com.korber.Order.service;

import com.korber.Order.config.Configuration;
import com.korber.Order.dto.InventoryBatch;
import com.korber.Order.dto.InventoryUpdateRequest;
import com.korber.Order.dto.OrderRequest;
import com.korber.Order.model.Orders;
import com.korber.Order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Configuration configuration;

    public Orders placeOrder(OrderRequest request) {
        // Check inventory
        String inventoryUrl = configuration.getInventoryServiceUrl() + request.getProductId();
        // List<InventoryBatch> inventoryBatchList = restTemplate.getForObject(inventoryUrl, InventoryBatch.class);

        ResponseEntity<List<InventoryBatch>> response = restTemplate.exchange(
                inventoryUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InventoryBatch>>() {
                }
        );

        List<InventoryBatch> inventoryBatchList = response.getBody();


        if (Objects.nonNull(inventoryBatchList)) {
            // Save order
            Orders order = new Orders();
            order.setProductId(request.getProductId());
            order.setQuantity(request.getQuantity());
            order.setOrderDate(LocalDateTime.now());
            orderRepository.save(order);

            // Update inventory

            int remainingQty = request.getQuantity();

            for (InventoryBatch batch : inventoryBatchList) {
                if (remainingQty <= 0) break;

                int availableQty = batch.getQuantity();
                int qtyToUpdate = availableQty - remainingQty;
                if (!(qtyToUpdate >= 0)) {
                    qtyToUpdate = 0;
                    remainingQty = -qtyToUpdate;
                }else {
                    remainingQty=0;
                }
                InventoryUpdateRequest updateRequest = new InventoryUpdateRequest();
                updateRequest.setProductId(request.getProductId());
                updateRequest.setBatchId(batch.getId());
                updateRequest.setQuantity(qtyToUpdate);
                updateRequest.setExpiryDate(batch.getExpiryDate());

                String updateUrl = configuration.getInventoryServiceUrl() + "/update";
                HttpEntity<InventoryUpdateRequest> requestEntity = new HttpEntity<>(updateRequest);
                restTemplate.exchange(
                        updateUrl,
                        HttpMethod.POST,
                        requestEntity,
                        String.class
                );


            }

            if (remainingQty > 0) {
                throw new RuntimeException("Insufficient inventory to fulfill the order. Short by " + remainingQty + " units.");
            }

            return order;
        } else {
            throw new RuntimeException("Product not available in inventory");
        }
    }
}
