package com.luckylogistics.slack.application.external;

import com.luckylogistics.slack.application.dto.OrderCreatedResult;

public interface SlackClient {

	void sendMessage(OrderCreatedResult command, String receiverEmail, String aiPrompt);

	boolean existsByEmail(String email);

}
