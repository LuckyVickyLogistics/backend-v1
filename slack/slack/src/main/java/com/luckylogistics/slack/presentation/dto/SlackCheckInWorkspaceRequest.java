package com.luckylogistics.slack.presentation.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SlackCheckInWorkspaceRequest(

	@NotNull(message = "이메일이 입력되지 않았습니다.")
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	String email

) {
}
