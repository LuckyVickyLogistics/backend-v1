package com.luckylogistics.product.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductUpdate (
        @NotBlank(message ="상품 이름은 필수입니다.")
        String productName,
        @NotNull(message ="상품 가격은 필수입니다.")
        @Min(value = 0, message = "상품 가격은 0원 이상이어야 합니다.")
        Integer price,
        @NotNull(message ="재고 값은 필수 입니다.")
        @Min(value = 0, message = "재고의 개수는 0개 이상이어야 합니다.")
        Integer totalQuantity,
        @NotNull(message ="초기 재고값은 필수값입니다..")
        @Min(value = 0, message = "초기 제품 개수는 0개 이상이어야 합니다.")
        Integer quantity   // 남은 재고
) {

}
