package com.luckylogistics.product.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {
    SOLD_OUT("품절"),
    ON_SALE("판매중"),
    HIDDEN("비활성"),
    DELETED("삭제됨");

    private String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAvailableForSale() {
        return this == ProductStatus.ON_SALE;
    }

    public static ProductStatus fromQuantity(int quantity) {
        if (quantity <= 0) {
            return SOLD_OUT;
        }
        return ON_SALE;
    }

    public ProductStatus hidden() {
        return HIDDEN;
    }
}
