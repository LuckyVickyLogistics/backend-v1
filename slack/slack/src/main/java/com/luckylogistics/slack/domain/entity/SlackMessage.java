package com.luckylogistics.slack.domain.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.luckylogistics.slack.domain.event.SlackMessageSavedEvent;
import com.luckylogistics.slack.domain.vo.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_slack_message")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SlackMessage extends BaseEntity<SlackMessage> {

	@Id
	@UuidGenerator
	@Column(name = "slack_message_id")
	private UUID slackMessageId;

	@Column(name = "receiver_email", nullable = false)
	private String receiverEmail;

	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String content;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable = false)
	private Status status;

	@Builder
	private SlackMessage(String receiverEmail, String content) {
		this.receiverEmail = receiverEmail;
		this.content = content;
		this.status = Status.PENDING;

		this.registerEvent(new SlackMessageSavedEvent(status.getDescription()));
	}

	public void updateStatus(Status newStatus) {
		if (!status.canTransitionTo(newStatus)) {
			throw new IllegalArgumentException(("'%s' 상태에서 '%s' 상태로 변경할 수 없습니다.")
				.formatted(status.getDescription(), newStatus.getDescription()));
		}
		this.status = newStatus;

		this.registerEvent(new SlackMessageSavedEvent(status.getDescription()));
	}

}
