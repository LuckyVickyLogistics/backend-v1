package com.luckylogistics.slack.infrastructure.external.kafka.event;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record OrderCreatedEvent(

	UUID orderId,

	String customerName,

	String customerEmail,

	Instant orderedAt,

	String receiverEmail,

	String productName,

	int quantity,

	String request,

	String startPoint,

	List<String> waypoints,

	String endPoint,

	String deliveryManagerName,

	String deliveryManagerEmail,

	LocalTime deliveryManagerStartTime,

	LocalTime deliveryManagerEndTime,

	Instant occurredAt

) {
}
