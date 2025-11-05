package com.luckylogistics.slack.application.external;

import java.time.LocalTime;

import com.luckylogistics.slack.application.result.AiPromptCreatedResult;
import com.luckylogistics.slack.application.result.OrderCreatedResult;

public interface AiServiceClient {

	AiPromptCreatedResult generateAiPrompt(OrderCreatedResult result, LocalTime startTime, LocalTime endTime);

}
