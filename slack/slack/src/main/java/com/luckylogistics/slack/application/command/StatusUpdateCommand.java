package com.luckylogistics.slack.application.command;

import lombok.Builder;

@Builder
public record StatusUpdateCommand(

	String status

) {
}
