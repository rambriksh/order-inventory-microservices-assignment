package com.korber.Order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryBatch {

    private Long id;
    private Product product;
    private int quantity;
    private LocalDate expiryDate;
}