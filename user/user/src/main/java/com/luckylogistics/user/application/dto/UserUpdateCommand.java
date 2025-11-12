package com.luckylogistics.user.application.dto;

import lombok.Builder;

@Builder
public record UserUpdateCommand(
	Long userId,
	String slackId
	//String organizationName
) {}
