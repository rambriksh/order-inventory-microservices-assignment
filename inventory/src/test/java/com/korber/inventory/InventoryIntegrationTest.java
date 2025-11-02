package com.korber.inventory;

import com.korber.inventory.model.InventoryBatch;
import com.korber.inventory.model.Product;
import com.korber.inventory.repository.InventoryBatchRepository;
import com.korber.inventory.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
class InventoryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private InventoryBatchRepository batchRepo;

    @BeforeEach
    void setup() {
        Product product = productRepo.save(new Product(null, "Laptop"));
        batchRepo.save(new InventoryBatch(null, product, 10, LocalDate.of(2025, 12, 31)));
        batchRepo.save(new InventoryBatch(null, product, 5, LocalDate.of(2025, 11, 30)));
    }

    @Test
    void testGetInventoryBatches() throws Exception {
        mockMvc.perform(get("/inventory/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].expiryDate").value("2025-11-30"));
    }

    @Test
    void testUpdateInventoryEndpoint() throws Exception {
        String json = """
            {
              "productId": 1,
              "quantity": 15,
              "expiryDate": "2026-01-01"
            }
        """;

        mockMvc.perform(post("/inventory/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Inventory updated"));
    }
}
