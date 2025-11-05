package com.luckylogistics.product.presentation.dto;

import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        int totalQuantity,
        int price,
        int currentQuantity,
        String status,
        boolean deleted
) {
}
