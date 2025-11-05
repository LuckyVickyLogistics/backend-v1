package com.luckylogistics.slack.infrastructure.external.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.luckylogistics.slack.application.event.OrderCreatedEvent;
import com.luckylogistics.slack.application.event.SlackEventListener;
import com.luckylogistics.slack.application.event.SlackKafkaEventHandler;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SlackEventListenerImpl implements SlackEventListener {

	private final SlackKafkaEventHandler kafkaEventHandler;

	@Override
	@KafkaListener(topics = "order-created", groupId = "slack-service-group")
	public void handleOrderCreated(OrderCreatedEvent event) {
		System.out.println("Kafka 수신 이벤트: " + event);
		kafkaEventHandler.test();
	}

}
