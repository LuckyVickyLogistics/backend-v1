package com.luckylogistics.order.infrastructure.client.dto;

import java.util.UUID;

import com.luckylogistics.order.application.dto.ProductResponse;

public record GetProductClientResponse(

	UUID productId,
	UUID companyId,
	UUID hubId,
	String productName,
	int price,
	int totalQuantity,
	int quantity,
	String status

) {

	public static ProductResponse of(GetProductClientResponse productResponse) {
		return ProductResponse.builder()
			.productName(productResponse.productName)
			.departureCompanyId(productResponse.companyId)
			.departureHubId(productResponse.hubId)
			.quantity(productResponse.quantity)
			.build();
	}

}
