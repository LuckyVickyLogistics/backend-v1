package com.luckylogistics.order.infrastructure.client;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeliveryDummyClient {
    public boolean isDelieveryExists(UUID delieveryId) {
        return true;
    }
}
