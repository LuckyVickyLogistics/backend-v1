package com.luckylogistics.slack.infrastructure.external.client;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.luckylogistics.slack.application.external.AiServiceClient;
import com.luckylogistics.slack.application.dto.AiPromptCreatedResult;
import com.luckylogistics.slack.application.dto.OrderCreatedResult;
import com.luckylogistics.slack.infrastructure.external.client.dto.AiPromptResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AiServiceClientAdapter implements AiServiceClient {

	private final AiServiceFeignClient feignClient;

	// TODO: AI 서비스가 개발된 후 실제 feignClient로 요청
	public AiPromptCreatedResult generateAiPrompt(OrderCreatedResult result, LocalTime startTime, LocalTime endTime) {
		return AiPromptResponse.of(new AiPromptResponse("2025-11-05T14:00:00Z"));
		// return AiPromptResponse.of(feignClient.generateAiPrompt(AiPromptRequest.from(result, startTime, endTime)));
	}

}
