package com.luckylogistics.ai.presentation.dto;

import java.time.Instant;

import com.luckylogistics.ai.application.dto.AiPromptResult;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record AiPromptCreatedResponse(

	Instant responseContent

) {

	public static AiPromptCreatedResponse from(AiPromptResult result) {
		return AiPromptCreatedResponse.builder()
			.responseContent(result.responseContent())
			.build();
	}

}
