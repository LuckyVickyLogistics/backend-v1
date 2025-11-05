package com.luckylogistics.slack.infrastructure.external.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.luckylogistics.slack.application.event.SlackEventPublisher;
import com.luckylogistics.slack.application.event.OrderCreatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SlackEventPublisherImpl implements SlackEventPublisher {

	private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

	@Override
	public void publish(OrderCreatedEvent event) {
		kafkaTemplate.send("order-created", event);
	}

}
