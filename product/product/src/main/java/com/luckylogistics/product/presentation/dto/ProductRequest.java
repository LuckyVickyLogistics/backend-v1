package com.luckylogistics.product.presentation.dto;

import java.util.UUID;

public record ProductRequest(
        String productName,
        Integer price,
        Integer totalQuantity,
        UUID companyId,
        UUID hubId,
        Integer initialQuantity
) {

}
