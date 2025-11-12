package com.luckylogistics.delivery.infrastructure.client.dto;

import com.luckylogistics.delivery.common.enums.OrderStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
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
    // 응답 데이터 유효성 검증
    public void validate() {
        if (orderId == null) {
            throw new IllegalStateException("[OrderClient] orderId가 유효하지 않습니다");
        }
        if (supplierId == null) {
            throw new IllegalStateException("[OrderClient] supplierId가 유효하지 않습니다");
        }
        if (customerId == null) {
            throw new IllegalStateException("[OrderClient] customerId가 유효하지 않습니다");
        }
    }

    public static OrderResponse of(UUID orderId, UUID supplierCompanyId, UUID customerCompanyId) {
        return OrderResponse.builder()
                .orderId(orderId)
                .supplierId(supplierCompanyId)
                .customerId(customerCompanyId)
                .build();
    }
}