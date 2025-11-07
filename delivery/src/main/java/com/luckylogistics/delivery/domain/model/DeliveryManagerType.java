package com.luckylogistics.delivery.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public enum DeliveryManagerType {
    HUB_DELIVERY("허브 배송 담당자") {
        @Override
        public void validateHubId(UUID hubId) {
            if (hubId != null) {
                throw new IllegalArgumentException("허브 배송 담당자는 Hub ID를 가질 수 없습니다");
            }
        }
    },

    COMPANY_DELIVERY("업체 배송 담당자") {
        @Override
        public void validateHubId(UUID hubId) {
            if (hubId == null) {
                throw new IllegalArgumentException("업체 배송 담당자는 Hub ID가 필요합니다");
            }
        }
    };

    private final String description;

    public boolean isHubDeliveryManager() {
        return this == HUB_DELIVERY;
    }

    public boolean isCompanyDeliveryManager() {
        return this == COMPANY_DELIVERY;
    }

    // 허브 ID 유효성 검증 (각 타입의 비즈니스 규칙)
    public abstract void validateHubId(UUID hubId);
}