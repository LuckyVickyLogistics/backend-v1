package com.luckylogistics.slack.infrastructure.external.client;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luckylogistics.slack.application.dto.AiPromptCreatedResult;
import com.luckylogistics.slack.application.dto.OrderCreatedResult;
import com.luckylogistics.slack.application.external.AiServiceClient;
import com.luckylogistics.slack.common.exception.BusinessException;
import com.luckylogistics.slack.common.exception.ErrorCode;
import com.luckylogistics.slack.common.response.ApiResponse;
import com.luckylogistics.slack.infrastructure.external.client.dto.AiPromptRequest;
import com.luckylogistics.slack.infrastructure.external.client.dto.AiPromptResponse;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AiServiceClientAdapter implements AiServiceClient {

	private final AiServiceFeignClient feignClient;

	private final ObjectMapper mapper;

	public AiPromptCreatedResult generateAiPrompt(OrderCreatedResult result, LocalTime startTime, LocalTime endTime) {
		try {
			ApiResponse<AiPromptResponse> response = feignClient.generateAiPrompt(AiPromptRequest.from(result, startTime, endTime));
			return AiPromptResponse.of(response.data());
		} catch (FeignException e) {
			String body = e.contentUTF8();
			try {
				throw new RuntimeException(mapper.readTree(body).get("message").asText());
			} catch (JsonProcessingException ex) {
				throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
			}
		}
	}

}
