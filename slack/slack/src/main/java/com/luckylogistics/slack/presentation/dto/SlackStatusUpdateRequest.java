package com.luckylogistics.slack.presentation.dto;

import org.jetbrains.annotations.NotNull;

import lombok.Builder;

@Builder
public record SlackStatusUpdateRequest(

	@NotNull
	String status

) {
}
