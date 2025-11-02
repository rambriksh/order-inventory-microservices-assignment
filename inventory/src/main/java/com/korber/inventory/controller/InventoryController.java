package com.korber.inventory.controller;

import com.korber.inventory.dto.InventoryUpdateRequest;
import com.korber.inventory.model.InventoryBatch;
import com.korber.inventory.service.InventoryServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryServiceFactory factory;

    @GetMapping(value = "/{productId}",produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<InventoryBatch> getBatches(@PathVariable Long productId) {
        return factory.getService("default").getBatchesSortedByExpiry(productId);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> updateInventory(@RequestBody InventoryUpdateRequest request) {
        factory.getService("default").updateInventory(request);
        return ResponseEntity.ok("Inventory updated");
    }
}
