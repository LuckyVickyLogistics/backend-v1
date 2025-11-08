package com.luckylogistics.ai.application.dto;

import com.luckylogistics.ai.presentation.dto.AiPromptStatusUpdateRequest;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record StatusUpdateCommand(

	String status

) {

	public static StatusUpdateCommand from(AiPromptStatusUpdateRequest requestDto) {
		return StatusUpdateCommand.builder().status(requestDto.status()).build();
	}

}
