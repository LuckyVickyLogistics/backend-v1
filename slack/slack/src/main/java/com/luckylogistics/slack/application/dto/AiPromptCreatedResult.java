package com.luckylogistics.slack.application.dto;

import java.time.Instant;

import lombok.Builder;

@Builder
public record AiPromptCreatedResult(

	Instant responseContent

) {
}
