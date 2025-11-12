package com.luckylogistics.delivery.infrastructure.client.dto;

import lombok.Builder;

import java.util.UUID;

// TODO: Order Service 연동
@Builder
public record OrderResponse(
        UUID orderId,             // 주문 ID
        UUID supplierCompanyId,   // 공급업체 ID
        UUID customerCompanyId    // 수령업체 ID
) {
    /**
     * 응답 데이터 유효성 검증
     */
    public void validate() {
        if (orderId == null) {
            throw new IllegalStateException("[OrderClient] orderId가 유효하지 않습니다");
        }
        if (supplierCompanyId == null) {
            throw new IllegalStateException("[OrderClient] supplierCompanyId가 유효하지 않습니다");
        }
        if (customerCompanyId == null) {
            throw new IllegalStateException("[OrderClient] customerCompanyId가 유효하지 않습니다");
        }
    }

    public static OrderResponse of(UUID orderId, UUID supplierCompanyId, UUID customerCompanyId) {
        return OrderResponse.builder()
                .orderId(orderId)
                .supplierCompanyId(supplierCompanyId)
                .customerCompanyId(customerCompanyId)
                .build();
    }
}