package com.korber.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryUpdateRequest {
    private Long productId;
    private Long batchId;
    private int quantity;
    private LocalDate expiryDate;
}