package com.luckylogistics.slack.presentation.dto;

import com.luckylogistics.slack.application.dto.SlackEmailCheckResult;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record SlackCheckInWorkSpaceResponse(

	boolean exists

) {

	public static SlackCheckInWorkSpaceResponse from(SlackEmailCheckResult result) {
		return SlackCheckInWorkSpaceResponse.builder()
			.exists(result.exists())
			.build();
	}

}
