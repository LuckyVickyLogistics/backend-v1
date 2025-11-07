package com.luckylogistics.ai.presentation.dto;

import java.time.Instant;
import java.util.UUID;

import com.luckylogistics.ai.application.dto.AiPromptReadResult;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record AiPromptSummaryResponse(

	UUID aiPromptId,

	String status,

	Instant createdAt,

	Long createdBy,

	Instant updatedAt,

	Long updatedBy

) {

	public static AiPromptSummaryResponse from(AiPromptReadResult result) {
		return AiPromptSummaryResponse.builder()
			.aiPromptId(result.aiPromptId())
			.status(result.status().name())
			.createdAt(result.createdAt())
			.createdBy(result.createdBy())
			.updatedAt(result.updatedAt())
			.updatedBy(result.updatedBy())
			.build();
	}

}
