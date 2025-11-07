package com.luckylogistics.ai.application.dto;

import java.time.Instant;
import java.util.UUID;

import com.luckylogistics.ai.domain.entity.AiPrompt;
import com.luckylogistics.ai.domain.vo.Status;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record AiPromptReadResult(

	UUID aiPromptId,

	String requestContent,

	Instant responseContent,

	Status status,

	Instant createdAt,

	Long createdBy,

	Instant updatedAt,

	Long updatedBy

) {

	public static AiPromptReadResult from(AiPrompt aiPrompt) {
		return AiPromptReadResult.builder()
			.aiPromptId(aiPrompt.getAiPromptId())
			.requestContent(aiPrompt.getRequestContent())
			.responseContent(aiPrompt.getResponseContent())
			.status(aiPrompt.getStatus())
			.createdAt(aiPrompt.getCreatedAt())
			.createdBy(aiPrompt.getCreatedBy())
			.updatedAt(aiPrompt.getUpdatedAt())
			.updatedBy(aiPrompt.getUpdatedBy())
			.build();
	}

}
