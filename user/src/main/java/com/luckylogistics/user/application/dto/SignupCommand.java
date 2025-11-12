package com.luckylogistics.user.application.dto;

import lombok.Builder;

@Builder
public record SignupCommand(
	String username,
	String password,
	String slackId,
	String role,
	String organizationType
) {}
