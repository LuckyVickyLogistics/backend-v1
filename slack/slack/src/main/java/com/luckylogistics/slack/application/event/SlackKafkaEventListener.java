package com.luckylogistics.slack.application.event;

import com.luckylogistics.slack.infrastructure.external.kafka.event.OrderCreatedEvent;

public interface SlackKafkaEventListener {

	void handleOrderCreated(OrderCreatedEvent event);

}
