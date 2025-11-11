package com.luckylogistics.slack.presentation.dto;

import com.luckylogistics.slack.application.dto.EmailCheckCommand;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record SlackCheckInWorkspaceRequest(

	@NotNull(message = "이메일이 입력되지 않았습니다.")
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	String email

) {

	public static EmailCheckCommand of(SlackCheckInWorkspaceRequest requestDto) {
		return EmailCheckCommand.builder()
			.email(requestDto.email())
			.build();
	}

}
