package com.luckylogistics.slack.infrastructure.external.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.luckylogistics.slack.application.dto.OrderCreatedResult;
import com.luckylogistics.slack.infrastructure.external.kafka.event.OrderCreatedEvent;
import com.luckylogistics.slack.application.event.SlackKafkaEventListener;
import com.luckylogistics.slack.application.event.SlackKafkaEventHandler;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SlackKafkaEventListenerImpl implements SlackKafkaEventListener {

	private final SlackKafkaEventHandler kafkaEventHandler;

	@Override
	@KafkaListener(topics = "order-created", groupId = "slack-service-group")
	public void handleOrderCreated(OrderCreatedEvent event) {
		OrderCreatedResult result = OrderCreatedResult.builder()
			.orderId(event.orderId())
			.customerName(event.customerName())
			.customerEmail(event.customerEmail())
			.orderedAt(event.orderedAt())
			.productName(event.productName())
			.quantity(event.quantity())
			.request(event.request())
			.startPoint(event.startPoint())
			.waypoints(event.waypoints())
			.endPoint(event.endPoint())
			.deliveryManagerName(event.deliveryManagerName())
			.deliveryManagerEmail(event.deliveryManagerEmail())
			.build();

		kafkaEventHandler.sendMessage(result, event.receiverEmail(), event.deliveryManagerStartTime(), event.deliveryManagerEndTime());
	}

}
