package com.luckylogistics.product.presentation.dto;

public record ProductUpdate (
    String productName,
    Integer price,
    Integer totalQuantity,
    Integer quantity   // 남은 재고
) {

}
