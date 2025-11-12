package com.luckylogistics.ai.application.dto;

import lombok.Builder;

@Builder
public record StatusUpdateCommand(

	String status

) {
}
