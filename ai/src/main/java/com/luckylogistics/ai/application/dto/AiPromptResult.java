package com.luckylogistics.ai.application.dto;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record AiPromptResult(

	Instant responseContent

) {

	public static AiPromptResult from(Instant responseContent) {
		return AiPromptResult.builder()
			.responseContent(responseContent)
			.build();
	}

}
