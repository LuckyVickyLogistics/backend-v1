package com.luckylogistics.product.domain.entity;

import lombok.Getter;

@Getter
public enum ProductStatus {
    SOLD_OUT("품절"),
    ON_SALE("판매중"),
    HIDDEN("비활성"),
    DELETED("삭제됨");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public static ProductStatus fromQuantity(int quantity) {
        if (quantity == 0) {
            return SOLD_OUT;
        }
        return ON_SALE;
    }
}
