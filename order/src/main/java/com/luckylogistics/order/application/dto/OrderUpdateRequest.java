package com.luckylogistics.order.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderUpdateRequest(
        @NotNull(message = "수정할 수량값은 필수입니다.")
        @Min(value = 1, message = "주문 수량은 1 이상이어야 합니다.")
        Integer Quantity,
        @NotBlank(message = "요청값은 필수입니다.")
        String request
) {

}
