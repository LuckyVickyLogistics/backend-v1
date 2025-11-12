package com.luckylogistics.order.infrastructure.kafka;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.luckylogistics.order.application.event.OrderKafkaEventPublisher;
import com.luckylogistics.order.infrastructure.kafka.event.OrderCreatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderKafkaEventPublisherImpl implements OrderKafkaEventPublisher {

	private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

	@Override
	public void publish(
		UUID orderId, String customerName, String customerEmail, Instant orderedAt, String productName,
		int quantity, String request, String startPoint, List<String> waypoints, String endPoint,
		String deliveryManagerName, String deliveryManagerEmail,
		LocalTime deliveryManagerStartTime, LocalTime deliveryManagerEndTime, String receiverEmail
	) {
		OrderCreatedEvent event = OrderCreatedEvent.builder()
			.orderId(orderId)
			.customerName(customerName)
			.customerEmail(customerEmail)
			.orderedAt(orderedAt)
			.productName(productName)
			.quantity(quantity)
			.request(request)
			.startPoint(startPoint)
			.waypoints(waypoints)
			.endPoint(endPoint)
			.deliveryManagerName(deliveryManagerName)
			.deliveryManagerEmail(deliveryManagerEmail)
			.deliveryManagerStartTime(deliveryManagerStartTime)
			.deliveryManagerEndTime(deliveryManagerEndTime)
			.receiverEmail(receiverEmail)
			.orderedAt(Instant.now())
			.build();
		kafkaTemplate.send("order-created", event);
	}

}
