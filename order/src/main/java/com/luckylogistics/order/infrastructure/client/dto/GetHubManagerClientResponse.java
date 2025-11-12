package com.luckylogistics.order.infrastructure.client.dto;

import com.luckylogistics.order.application.dto.HubManagerEmailResponse;

public record GetHubManagerClientResponse(

	String slackId
	// TODO: 나머지 필드 추가

) {

	public static HubManagerEmailResponse of(GetHubManagerClientResponse response) {
		return HubManagerEmailResponse.builder().slackId(response.slackId()).build();
	}

}
