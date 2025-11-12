package com.luckylogistics.order.application.event;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface OrderKafkaEventPublisher {

	void publish(
		UUID orderId, String customerName, String customerEmail, Instant orderedAt, String productName,
		int quantity, String request, String startPoint, List<String> waypoints, String endPoint,
		String deliveryManagerName, String deliveryManagerEmail,
		LocalTime deliveryManagerStartTime, LocalTime deliveryManagerEndTime, String receiverEmail
	);
}
