package com.korber.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class InventoryUpdateRequest {
    private Long productId;
    private Long batchId;
    private int quantity;
    private LocalDate expiryDate;
}