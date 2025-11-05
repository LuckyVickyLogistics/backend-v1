package com.luckylogistics.slack.application.command;

import lombok.Builder;

@Builder
public record EmailCheckCommand(

	String email

) {
}
