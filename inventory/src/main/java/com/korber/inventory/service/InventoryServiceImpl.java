package com.korber.inventory.service;

import com.korber.inventory.dto.InventoryUpdateRequest;
import com.korber.inventory.model.InventoryBatch;
import com.korber.inventory.model.Product;
import com.korber.inventory.repository.InventoryBatchRepository;
import com.korber.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryBatchRepository batchRepo;
    @Autowired
    private ProductRepository productRepo;

    @Override
    public List<InventoryBatch> getBatchesSortedByExpiry(Long productId) {
        return batchRepo.findByProductIdOrderByExpiryDateAsc(productId);
    }

    @Override
    public void updateInventory(InventoryUpdateRequest request) {
        Product product = productRepo.findById(request.getProductId()).orElseThrow();
        InventoryBatch batch = new InventoryBatch();
        batch.setId(request.getBatchId());
        batch.setProduct(product);
        batch.setQuantity(request.getQuantity());
        batch.setExpiryDate(request.getExpiryDate());
        batchRepo.save(batch);
    }
}
