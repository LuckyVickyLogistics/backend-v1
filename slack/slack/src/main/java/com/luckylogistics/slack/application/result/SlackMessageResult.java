package com.luckylogistics.slack.application.result;

import java.time.Instant;
import java.util.UUID;

import com.luckylogistics.slack.domain.entity.SlackMessage;
import com.luckylogistics.slack.domain.vo.Status;

import lombok.Builder;

@Builder
public record SlackMessageResult(

	UUID slackMessageId,

	String receiverEmail,

	String content,

	Status status,

	Instant createdAt,

	Long createdBy,

	Instant updatedAt,

	Long updatedBy

) {

	public static SlackMessageResult from(SlackMessage slackMessage) {
		return new SlackMessageResult(
			slackMessage.getSlackMessageId(),
			slackMessage.getReceiverEmail(),
			slackMessage.getContent(),
			slackMessage.getStatus(),
			slackMessage.getCreatedAt(),
			slackMessage.getCreatedBy(),
			slackMessage.getUpdatedAt(),
			slackMessage.getUpdatedBy()
		);
	}

}
