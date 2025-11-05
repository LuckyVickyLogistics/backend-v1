package com.luckylogistics.product.presentation.dto;

import java.util.UUID;

public record ProductRequest(
        String name,
        Integer price,
        Integer Quantity,
        UUID companyId,
        UUID hubId,
        Integer initialQuantity
) {

}
