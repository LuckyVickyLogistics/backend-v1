package com.luckylogistics.slack.infrastructure.external.client.dto;

import com.luckylogistics.slack.application.dto.AiPromptCreatedResult;

public record AiPromptResponse(

	String responseContent

) {

	public static AiPromptCreatedResult of(AiPromptResponse aiPromptResponse) {
		return AiPromptCreatedResult.builder()
			.responseContent(aiPromptResponse.responseContent)
			.build();
	}

}
