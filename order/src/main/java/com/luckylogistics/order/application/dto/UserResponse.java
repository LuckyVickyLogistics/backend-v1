package com.luckylogistics.order.application.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record UserResponse(

	String username,
	String slackId,
	UUID companyId

) {
}
