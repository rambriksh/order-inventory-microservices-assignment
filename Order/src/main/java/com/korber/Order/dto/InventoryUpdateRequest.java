package com.korber.Order.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InventoryUpdateRequest {
    private Long productId;
    private Long batchId;
    private int quantity;
    private LocalDate expiryDate;
}

