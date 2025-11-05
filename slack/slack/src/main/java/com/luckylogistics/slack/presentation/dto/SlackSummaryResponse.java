package com.luckylogistics.slack.presentation.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record SlackSummaryResponse(

	UUID slackMessageId,

	String receiverEmail,

	String status

) {
}
