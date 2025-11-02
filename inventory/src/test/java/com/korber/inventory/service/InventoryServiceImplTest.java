package com.korber.inventory.service;

import com.korber.inventory.dto.InventoryUpdateRequest;
import com.korber.inventory.model.InventoryBatch;
import com.korber.inventory.model.Product;
import com.korber.inventory.repository.InventoryBatchRepository;
import com.korber.inventory.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryBatchRepository batchRepo;

    @Mock
    private ProductRepository productRepo;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @Test
    void testGetBatchesSortedByExpiry() {
        Long productId = 1L;
        List<InventoryBatch> mockBatches = List.of(
                new InventoryBatch(1L, null, 10, LocalDate.of(2025, 12, 31)),
                new InventoryBatch(2L, null, 5, LocalDate.of(2025, 11, 30))
        );

        when(batchRepo.findByProductIdOrderByExpiryDateAsc(productId)).thenReturn(mockBatches);

        List<InventoryBatch> result = inventoryService.getBatchesSortedByExpiry(productId);

        assertEquals(2, result.size());
        assertEquals(LocalDate.of(2025, 12, 31), result.get(0).getExpiryDate());
    }

    @Test
    void testUpdateInventory() {
        Product product = new Product(1L, "Laptop");
        InventoryUpdateRequest request = new InventoryUpdateRequest(1L, 10, LocalDate.of(2025, 12, 31));

        when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        inventoryService.updateInventory(request);

        verify(batchRepo, times(1)).save(any(InventoryBatch.class));
    }
}
