package com.luckylogistics.product.application.dto;

import java.util.UUID;
import jakarta.validation.constraints.*;

public record ProductRequest(
        @NotBlank(message ="상품 이름은 필수입니다.")
        String productName,
        @NotNull(message ="상품 가격은 필수입니다.")
        @Min(value = 0, message = "상품 가격은 0원 이상이어야 합니다.")
        Integer price,
        //남은 재고
        @NotNull(message ="재고 값은 필수 입니다.")
        @Min(value = 0, message = "재고의 개수는 0개 이상이어야 합니다.")
        Integer totalQuantity,
        @NotNull(message = "회사 ID는 필수입니다.")
        UUID companyId,
        @NotNull(message = "허브 ID는 필수입니다.")
        UUID hubId,
        //전체 재고
        @NotNull(message ="초기 재고값은 필수값입니다..")
        @Min(value = 0, message = "초기 제품 개수는 0개 이상이어야 합니다.")
        Integer initialQuantity
) {

}

//DTO는 Validation 걸기
