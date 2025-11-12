package com.luckylogistics.order.application.dto;

import lombok.AccessLevel;
import lombok.Builder;

@Builder
public record HubManagerEmailResponse(

	String slackId

) {

	public static HubManagerEmailResponse of(String slackId) {
		return HubManagerEmailResponse.builder()
			.slackId(slackId)
			.build();
	}

}
