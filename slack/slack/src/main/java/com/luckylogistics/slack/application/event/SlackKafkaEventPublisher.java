package com.luckylogistics.slack.application.event;

public interface SlackEventPublisher {

	void publish(OrderCreatedEvent event);

}
