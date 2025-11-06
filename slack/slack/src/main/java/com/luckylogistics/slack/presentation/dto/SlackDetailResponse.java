package com.luckylogistics.slack.presentation.dto;

import java.time.Instant;
import java.util.UUID;

import com.luckylogistics.slack.application.dto.SlackMessageResult;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record SlackDetailResponse(

	UUID slackMessageId,

	String receiverEmail,

	String content,

	String status,

	Instant createdAt,

	Long createdBy,

	Instant updatedAt,

	Long updatedBy

) {

	public static SlackDetailResponse from(SlackMessageResult result) {
		return SlackDetailResponse.builder()
			.slackMessageId(result.slackMessageId())
			.receiverEmail(result.receiverEmail())
			.content(result.content())
			.status(result.status().name())
			.createdAt(result.createdAt())
			.createdBy(result.createdBy())
			.updatedAt(result.updatedAt())
			.updatedBy(result.updatedBy())
			.build();
	}

}
