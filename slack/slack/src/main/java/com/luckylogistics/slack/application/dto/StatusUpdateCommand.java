package com.luckylogistics.slack.application.dto;

import lombok.Builder;

@Builder
public record StatusUpdateCommand(

	String status

) {
}
