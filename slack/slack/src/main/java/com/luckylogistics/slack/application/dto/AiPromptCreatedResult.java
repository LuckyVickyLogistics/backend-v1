package com.luckylogistics.slack.application.dto;

import lombok.Builder;

@Builder
public record AiPromptCreatedResult(

	String responseContent

) {
}
