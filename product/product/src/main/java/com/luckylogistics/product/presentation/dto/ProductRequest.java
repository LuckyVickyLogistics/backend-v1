package com.luckylogistics.product.presentation.dto;

import java.util.UUID;

public record ProductRequest(
        String productName,
        Integer price,
        //남은 재고
        Integer totalQuantity,
        UUID companyId,
        UUID hubId,
        //전체 재고
        Integer initialQuantity
) {

}
