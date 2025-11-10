package com.luckylogistics.slack.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SlackStatusUpdateRequest(

	@NotNull(message = "상태가 입력되지 않았습니다.")
	String status

) {
}
