package com.luckylogistics.ai.presentation.dto;

import java.time.Instant;
import java.util.UUID;

import com.luckylogistics.ai.application.dto.AiPromptReadResult;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record AiPromptDetailResponse(

	UUID aiPromptId,

	String requestContent,

	Instant responseContent,

	String status,

	Instant createdAt,

	Long createdBy,

	Instant updatedAt,

	Long updatedBy

) {

	public static AiPromptDetailResponse from(AiPromptReadResult result) {
		return AiPromptDetailResponse.builder()
			.aiPromptId(result.aiPromptId())
			.requestContent(result.requestContent())
			.responseContent(result.responseContent())
			.status(result.status().name())
			.createdAt(result.createdAt())
			.createdBy(result.createdBy())
			.updatedAt(result.updatedAt())
			.updatedBy(result.updatedBy())
			.build();
	}

}
