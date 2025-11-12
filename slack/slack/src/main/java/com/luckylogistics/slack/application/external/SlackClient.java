package com.luckylogistics.slack.application.external;

import java.time.Instant;

import com.luckylogistics.slack.application.dto.OrderCreatedResult;

public interface SlackClient {

	void sendMessage(OrderCreatedResult command, String receiverEmail, Instant aiPrompt);

	boolean existsByEmail(String email);

}
