package com.luckylogistics.slack.infrastructure.external.client.dto;

import com.luckylogistics.slack.application.result.AiPromptCreatedResult;

public record AiPromptResponse(

	String responseContent

) {

	public static AiPromptCreatedResult of(AiPromptResponse aiPromptResponse) {
		return AiPromptCreatedResult.builder()
			.responseContent(aiPromptResponse.responseContent)
			.build();
	}

}
