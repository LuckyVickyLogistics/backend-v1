package com.luckylogistics.slack.application.external;

import java.time.LocalTime;

import com.luckylogistics.slack.application.dto.AiPromptCreatedResult;
import com.luckylogistics.slack.application.dto.OrderCreatedResult;

public interface AiServiceClient {

	AiPromptCreatedResult generateAiPrompt(OrderCreatedResult result, LocalTime startTime, LocalTime endTime);

}
