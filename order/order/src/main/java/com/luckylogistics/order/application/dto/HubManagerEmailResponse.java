package com.luckylogistics.order.application.dto;

import lombok.Builder;

@Builder
public record HubManagerEmailResponse(

	String slackId

) {
}
