package com.luckylogistics.slack.infrastructure.external.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.luckylogistics.slack.application.event.SlackKafkaEventPublisher;
import com.luckylogistics.slack.infrastructure.external.kafka.event.OrderCreatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SlackKafkaEventPublisherImpl implements SlackKafkaEventPublisher {

	private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

	@Override
	public void publish(OrderCreatedEvent event) {
		kafkaTemplate.send("order-created", event);
	}

}
