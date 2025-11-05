package com.luckylogistics.slack.application.result;

import lombok.Builder;

@Builder
public record AiPromptCreatedResult(

	String responseContent

) {
}
