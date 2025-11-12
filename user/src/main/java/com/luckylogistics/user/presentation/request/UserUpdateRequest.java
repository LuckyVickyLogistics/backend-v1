package com.luckylogistics.user.presentation.request;

public record UserUpdateRequest(
	Long userId,
	String slackId
	//String organizationName
) {}
