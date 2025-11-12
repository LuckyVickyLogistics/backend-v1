package com.luckylogistics.user.application.dto;

import lombok.Builder;

@Builder
public record TokenResponse(
	String accessToken,
	String refreshToken
) {}
