package com.luckylogistics.company.infrastructure.client.dto.enums;

public enum OrganizationType {
    HUB,
    COMPANY;

    public static OrganizationType from(String type) {
        return OrganizationType.valueOf(type.toUpperCase());
    }
}
