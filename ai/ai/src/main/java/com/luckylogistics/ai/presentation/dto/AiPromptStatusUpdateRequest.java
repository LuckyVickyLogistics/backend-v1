package com.luckylogistics.ai.presentation.dto;

import jakarta.validation.constraints.NotNull;

public record AiPromptStatusUpdateRequest(

	@NotNull
	String status

) {
}
