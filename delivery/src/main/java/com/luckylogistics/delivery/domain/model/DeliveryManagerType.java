package com.luckylogistics.delivery.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryManagerType {
    HUB_DELIVERY("허브 배송 담당자"),
    COMPANY_DELIVERY("업체 배송 담당자");

    private final String description;

    public boolean isHubDeliveryManager() {
        return this == HUB_DELIVERY;
    }

    public boolean isCompanyDeliveryManager() {
        return this == COMPANY_DELIVERY;
    }
}