package com.luckylogistics.order.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PlusRequest (
        @NotNull(message ="재고 값은 필수 입니다.")
        @Min(value = 1, message = "더할 재고의 개수는 1개 이상이어야 합니다.")
        Integer amount
){
}
