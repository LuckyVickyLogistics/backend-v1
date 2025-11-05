package com.luckylogistics.slack.presentation.dto;

import org.jetbrains.annotations.NotNull;

import jakarta.validation.constraints.Email;
import lombok.Builder;

@Builder
public record SlackCheckInWorkspaceRequest(

	@NotNull
	@Email
	String email

) {
}
