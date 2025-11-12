package com.luckylogistics.order.application.dto;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(

	// @NotNull(message = "공급업체 id가 입력되지 않았습니다.")
	// UUID supplierId, // 상품이 속한 업체 id
	//
	// @NotNull(message = "수령업체 id가 입력되지 않았습니다.")
	// UUID customerId, // 로그인한 회원이 속한 업체 id

	@NotNull(message = "상품 id가 입력되지 않았습니다.")
	UUID productId,

	// @NotNull(message = "수령인 id가 입력되지 않았습니다.")
	// UUID recipientId,

	@NotBlank(message = "배송지 주소가 입력되지 않았습니다.")
	String deliveryAddress,

	@Min(value = 1, message = "수량은 1보다 커야 합니다.")
	int quantity,

	@NotBlank(message = "요청 사항을 입력해주세요.")
	String request

) {
}
