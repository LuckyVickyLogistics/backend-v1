package com.luckylogistics.ai.presentation.dto;

import java.time.Instant;

import com.luckylogistics.ai.application.dto.GeminiPromptResult;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record AiPromptCreatedResponse(

	Instant responseContent,

	String error

) {

	public static AiPromptCreatedResponse from(GeminiPromptResult result) {
		if (result.error() != null) {
			return AiPromptCreatedResponse.builder()
				.responseContent(null)
				.error(result.error())
				.build();
		}

		return AiPromptCreatedResponse.builder()
			.responseContent(result.responseContent())
			.error(null)
			.build();
	}

}
