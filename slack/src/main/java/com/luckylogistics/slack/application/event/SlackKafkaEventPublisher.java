package com.luckylogistics.slack.application.event;

import com.luckylogistics.slack.infrastructure.external.kafka.event.OrderCreatedEvent;

public interface SlackKafkaEventPublisher {

	void publish(OrderCreatedEvent event);

}
