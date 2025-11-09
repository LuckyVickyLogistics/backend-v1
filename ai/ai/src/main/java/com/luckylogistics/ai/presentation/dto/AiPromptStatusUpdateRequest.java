package com.luckylogistics.ai.presentation.dto;

import jakarta.validation.constraints.NotNull;

public record AiPromptStatusUpdateRequest(

	@NotNull(message = "상태가 입력되지 않았습니다.")
	String status

) {
}
