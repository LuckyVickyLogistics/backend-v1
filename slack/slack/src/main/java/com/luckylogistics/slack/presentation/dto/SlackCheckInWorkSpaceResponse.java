package com.luckylogistics.slack.presentation.dto;

import lombok.Builder;

@Builder
public record SlackCheckInWorkSpaceResponse(

	boolean exists

) {
}
