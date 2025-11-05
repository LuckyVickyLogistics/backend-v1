package com.luckylogistics.product.presentation.dto;

import com.luckylogistics.product.domain.entity.Product;

import java.util.UUID;

public record ProductResponse(
        UUID productId,
        String productName,
        int price,
        int totalQuantity,
        int quantity,
        String status
) {
    public static ProductResponse from(Product p) {
        return new ProductResponse(
                p.getProductId(),
                p.getProductName(),
                p.getPrice(),
                p.getTotalQuantity(),
                p.getQuantity().getValue(),
                p.getStatus().name()
        );
    }
}