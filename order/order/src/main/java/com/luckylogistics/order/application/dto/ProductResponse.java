package com.luckylogistics.order.application.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record ProductResponse(
	String productName,
	UUID departureCompanyId, // 공급 업체 ID
	UUID departureHubId, // 출발 허브 ID
	int quantity
) {
}
