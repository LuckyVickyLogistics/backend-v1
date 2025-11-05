package com.luckylogistics.slack.application.event;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

// TODO: receiverEmail 필드 추가
public record OrderCreatedEvent(

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

	String deliveryManagerEmail,

	Instant occurredAt

) {
}
