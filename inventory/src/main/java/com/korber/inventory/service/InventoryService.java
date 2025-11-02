package com.korber.inventory.service;

import com.korber.inventory.dto.InventoryUpdateRequest;
import com.korber.inventory.model.InventoryBatch;

import java.util.List;

public interface InventoryService {
    List<InventoryBatch> getBatchesSortedByExpiry(Long productId);
    void updateInventory(InventoryUpdateRequest request);
}
