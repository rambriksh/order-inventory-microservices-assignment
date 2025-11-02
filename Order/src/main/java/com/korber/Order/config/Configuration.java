package com.korber.Order.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class Configuration {

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

}
