package com.luckylogistics.slack.application.event;

public interface SlackEventListener {

	void handleOrderCreated(OrderCreatedEvent event);

}
