package com.luckylogistics.delivery.application.service;

import java.util.UUID;

public interface OrderService {
    void validateOrderExists(UUID orderId);
}