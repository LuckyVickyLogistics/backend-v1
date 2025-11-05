package com.luckylogistics.slack.application.event;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.luckylogistics.slack.application.result.OrderCreatedResult;
import com.luckylogistics.slack.application.service.SlackService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SlackKafkaEventHandler {

	private final SlackService slackService;

	public void sendMessage(OrderCreatedResult result, String receiverEmail, LocalTime startTime, LocalTime endTime) {
		slackService.sendMessage(result, receiverEmail,startTime, endTime);
	}

}
