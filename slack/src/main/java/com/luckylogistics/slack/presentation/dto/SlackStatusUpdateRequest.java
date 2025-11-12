package com.luckylogistics.slack.presentation.dto;

import com.luckylogistics.slack.application.dto.StatusUpdateCommand;

import jakarta.validation.constraints.NotNull;

public record SlackStatusUpdateRequest(

	@NotNull(message = "상태가 입력되지 않았습니다.")
	String status

) {

	public static StatusUpdateCommand of(SlackStatusUpdateRequest requestDto) {
		return StatusUpdateCommand.builder()
			.status(requestDto.status)
			.build();
	}

}
