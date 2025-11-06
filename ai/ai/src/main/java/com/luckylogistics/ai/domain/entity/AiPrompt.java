package com.luckylogistics.ai.domain.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.luckylogistics.ai.domain.vo.Status;

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
@Table(name = "p_ai_prompt")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiPrompt extends BaseEntity<AiPrompt>{

	@Id
	@UuidGenerator
	@Column(name = "ai_prompt_id")
	private UUID aiPromptId;

	@Column(name = "request_content", nullable = false, columnDefinition = "TEXT")
	private String requestContent;

	@Column(name = "response_content")
	private String responseContent;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private Status status;

	@Builder
	private AiPrompt(String requestContent) {
		this.requestContent = requestContent;
		this.status = Status.PENDING;
	}

	public void updateStatus(String strStatus) {
		Status newStatus = validateStatus(strStatus);

		if (!status.canTransitionTo(newStatus)) {
			throw new IllegalArgumentException(("'%s' 상태에서 '%s' 상태로 변경할 수 없습니다.")
				.formatted(status.getDescription(), newStatus.getDescription()));
		}
		this.status = newStatus;
	}

	private Status validateStatus(String strStatus) {
		if (strStatus == null || strStatus.isBlank()) {
			throw new IllegalArgumentException("상태 값이 비어있습니다.");
		}

		try {
			return Status.valueOf(strStatus.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("유효하지 않은 상태 값입니다.");
		}
	}

}
