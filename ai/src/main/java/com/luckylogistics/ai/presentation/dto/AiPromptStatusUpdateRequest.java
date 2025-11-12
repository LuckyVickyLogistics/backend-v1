package com.luckylogistics.ai.presentation.dto;

import com.luckylogistics.ai.application.dto.StatusUpdateCommand;

import jakarta.validation.constraints.NotNull;

public record AiPromptStatusUpdateRequest(

	@NotNull(message = "상태가 입력되지 않았습니다.")
	String status

) {

	public static StatusUpdateCommand of(AiPromptStatusUpdateRequest requestDto) {
		return StatusUpdateCommand.builder().status(requestDto.status()).build();
	}

}
