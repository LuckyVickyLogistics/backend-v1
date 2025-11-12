package com.luckylogistics.delivery.common.enums;

public enum UserRole {
    MASTER_ADMIN , // 마스터 관리자
    HUB_MANAGER, // 허브 관리자
    DELIVERY_MANAGER, // 배송 담당자
    COMPANY_MANAGER; // 업체 담당자

    public boolean isMaster() {
        return this == MASTER_ADMIN;
    }

    public boolean isHubManager() {
        return this == HUB_MANAGER;
    }

    public boolean isDeliveryManager() {
        return this == DELIVERY_MANAGER;
    }

    public boolean isCompanyManager() {
        return this == COMPANY_MANAGER;
    }
}