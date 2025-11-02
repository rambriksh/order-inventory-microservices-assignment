package com.korber.inventory.controller;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import com.korber.inventory.dto.InventoryUpdateRequest;
import com.korber.inventory.model.InventoryBatch;
import com.korber.inventory.service.InventoryService;
import com.korber.inventory.service.InventoryServiceFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

    @InjectMocks
    private InventoryController inventoryController;

    @Mock
    private InventoryServiceFactory factory;

    @Mock
    private InventoryService inventoryService;

    @Test
    void testGetBatches() {
        Long productId = 1L;
        List<InventoryBatch> mockBatches = List.of(
                new InventoryBatch(1L, null, 10, LocalDate.of(2025, 12, 31)),
                new InventoryBatch(2L, null, 5, LocalDate.of(2025, 11, 30))
        );

        when(factory.getService("default")).thenReturn(inventoryService);
        when(inventoryService.getBatchesSortedByExpiry(productId)).thenReturn(mockBatches);

        List<InventoryBatch> result = inventoryController.getBatches(productId);

        assertEquals(2, result.size());
        assertEquals(LocalDate.of(2025, 12, 31), result.get(0).getExpiryDate());
    }

    @Test
    void testUpdateInventory() {
        InventoryUpdateRequest request = new InventoryUpdateRequest(1L, 10, LocalDate.of(2025, 12, 31));

        when(factory.getService("default")).thenReturn(inventoryService);
        doNothing().when(inventoryService).updateInventory(request);

        ResponseEntity<String> response = inventoryController.updateInventory(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Inventory updated", response.getBody());
    }
}
