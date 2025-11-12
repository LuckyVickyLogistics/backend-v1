package com.luckylogistics.order.domain.entity;

public enum OrderStatus {
    DELETED("삭제됨"),
    ORDERED("주문 완료");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }
}
