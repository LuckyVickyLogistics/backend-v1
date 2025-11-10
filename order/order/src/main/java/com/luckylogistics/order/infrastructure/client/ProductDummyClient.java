package com.luckylogistics.order.infrastructure.client;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductDummyClient {
    public boolean isProductExists(UUID productId) {
        return true;
    }
}
