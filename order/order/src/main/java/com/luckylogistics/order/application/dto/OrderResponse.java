package com.luckylogistics.order.application.dto;

import com.luckylogistics.order.domain.entity.OrderStatus;

import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        int quantity,
        String request,
        OrderStatus status,
        UUID supplierId,
        UUID customerId,
        UUID productId,
        UUID deliveryId
) {
}
