package com.luckylogistics.ai.application.dto;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GeminiPromptResult(

	Instant responseContent,

	String error

) {

	public static GeminiPromptResult from(Instant responseContent, String error) {
		return GeminiPromptResult.builder()
			.responseContent(responseContent)
			.error(error)
			.build();
	}

}
