package com.luckylogistics.slack.infrastructure.external.client.dto;

import java.time.Instant;

import com.luckylogistics.slack.application.dto.AiPromptCreatedResult;

public record AiPromptResponse(

	Instant responseContent

) {

	public static AiPromptCreatedResult of(AiPromptResponse aiPromptResponse) {
		return AiPromptCreatedResult.builder()
			.responseContent(aiPromptResponse.responseContent)
			.build();
	}

}
