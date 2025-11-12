package com.luckylogistics.product.infrastructure.client;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HubDummyClient {
    public boolean isHubExists(UUID hubId) {
        return true;
    }
}
