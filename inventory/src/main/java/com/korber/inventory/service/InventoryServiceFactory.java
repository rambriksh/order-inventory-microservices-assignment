package com.korber.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryServiceFactory {
    @Autowired
    private InventoryServiceImpl defaultService;

    public InventoryService getService(String type) {

        return defaultService;
    }
}
