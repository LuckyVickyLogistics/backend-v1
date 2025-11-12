package com.luckylogistics.order.application.dto;

import com.luckylogistics.order.domain.entity.Order;
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
    public static OrderResponse from (Order order) {
        return new OrderResponse(
                order.getOrderId(),
                order.getQuantity(),
                order.getRequest(),
                order.getStatus(),
                order.getSupplierId(),
                order.getCustomerId(),
                order.getProductId(),
                order.getDeliveryId()
        );
    }
}
