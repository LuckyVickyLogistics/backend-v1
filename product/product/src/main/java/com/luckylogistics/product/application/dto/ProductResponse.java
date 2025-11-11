package com.luckylogistics.product.application.dto;

import com.luckylogistics.product.domain.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

    public record ProductResponse(
            UUID productId,
            String productName,
            int price,
            int totalQuantity,
            int quantity,
            String status,
            UUID hubId
    ) {
    public static ProductResponse from(Product p) {
        return new ProductResponse(
                p.getProductId(),
                p.getProductName(),
                p.getPrice(),
                p.getTotalQuantity(),
                p.getQuantity().getValue(),
                p.getStatus().name(),
                p.getHubId()
        );
    }
}