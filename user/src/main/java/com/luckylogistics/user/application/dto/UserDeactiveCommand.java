package com.luckylogistics.user.application.dto;

import lombok.Builder;

@Builder
public record UserDeactiveCommand(
	Long userId
) {}
