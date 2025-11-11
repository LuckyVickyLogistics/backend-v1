package com.luckylogistics.product.application.dto;

import com.luckylogistics.product.domain.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProductResponse(
        @NotNull(message = "상품 ID은 필수입니다.")
        UUID productId,
        @NotBlank(message ="상품 이름은 필수입니다.")
        String productName,
        @NotNull(message ="상품 가격은 필수입니다.")
        @Min(value = 0, message = "상품 가격은 0원 이상이어야 합니다.")
        int price,
        @NotNull(message ="재고 값은 필수 입니다.")
        @Min(value = 0, message = "재고의 개수는 0개 이상이어야 합니다.")
        int totalQuantity,
        @NotNull(message ="초기 재고값은 필수값입니다..")
        @Min(value = 0, message = "초기 제품 개수는 0개 이상이어야 합니다.")
        int quantity,
        @NotNull(message = "상태값은 필수입니다.")
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