package com.luckylogistics.slack.application.result;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import lombok.Builder;

@Builder
public record OrderCreatedResult(

	UUID orderId,

	String customerName,

	String customerEmail,

	Instant orderedAt,

	String productName,

	int quantity,

	String request,

	String startPoint,

	List<String> waypoints,

	String endPoint,

	String deliveryManagerName,

	String deliveryManagerEmail

) {
}
