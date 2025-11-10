package com.luckylogistics.user.application.dto;

import lombok.Builder;

@Builder
public record LoginCommand(
	String username,
	String password
) {}
