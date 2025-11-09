package com.luckylogistics.slack.infrastructure.external.client;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.luckylogistics.slack.application.dto.AiPromptCreatedResult;
import com.luckylogistics.slack.application.dto.OrderCreatedResult;
import com.luckylogistics.slack.application.external.AiServiceClient;
import com.luckylogistics.slack.common.response.ApiResponse;
import com.luckylogistics.slack.infrastructure.external.client.dto.AiPromptRequest;
import com.luckylogistics.slack.infrastructure.external.client.dto.AiPromptResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AiServiceClientAdapter implements AiServiceClient {

	private final AiServiceFeignClient feignClient;

	public AiPromptCreatedResult generateAiPrompt(OrderCreatedResult result, LocalTime startTime, LocalTime endTime) {
		ApiResponse<AiPromptResponse> response = feignClient.generateAiPrompt(AiPromptRequest.from(result, startTime, endTime));
		if (!response.success()) {
			throw new RuntimeException(response.message());
		}
		return AiPromptResponse.of(response.data());
	}

}
