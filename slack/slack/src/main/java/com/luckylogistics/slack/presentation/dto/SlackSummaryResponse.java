package com.luckylogistics.slack.presentation.dto;

import java.util.UUID;

import com.luckylogistics.slack.application.dto.SlackMessageResult;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record SlackSummaryResponse(

	UUID slackMessageId,

	String receiverEmail,

	String status

) {

	public static SlackSummaryResponse from(SlackMessageResult result) {
		return SlackSummaryResponse.builder()
			.slackMessageId(result.slackMessageId())
			.receiverEmail(result.receiverEmail())
			.status(result.status().name())
			.build();
	}

}
