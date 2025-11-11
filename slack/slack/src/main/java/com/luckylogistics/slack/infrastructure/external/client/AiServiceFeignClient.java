package com.luckylogistics.slack.infrastructure.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.luckylogistics.slack.infrastructure.external.client.dto.AiPromptRequest;
import com.luckylogistics.slack.infrastructure.external.client.dto.AiPromptResponse;

@FeignClient(name = "ai")
public interface AiServiceFeignClient {

	@PostMapping("/api/v1/ai-prompts")
	AiPromptResponse generateAiPrompt(AiPromptRequest requestDto);

}
