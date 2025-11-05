package com.luckylogistics.slack.presentation.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;

@Builder
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
}
