package com.luckylogistics.order.infrastructure.client.dto;

import java.util.UUID;

import com.luckylogistics.order.application.dto.UserResponse;

public record GetUserClientResponse(

	// TODO: 나머지 필드 추가
	String username,
	String slackId,
	UUID organizationId

) {

	public static UserResponse of(GetUserClientResponse response) {
		return UserResponse.builder()
			.username(response.username)
			.slackId(response.slackId)
			.companyId(response.organizationId)
			.build();
	}

}
