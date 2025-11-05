package com.luckylogistics.slack.infrastructure.external.client.dto;

import java.time.LocalTime;
import java.util.List;

import com.luckylogistics.slack.application.result.OrderCreatedResult;

import lombok.Builder;

@Builder
public record AiPromptRequest(

	String productName,

	int quantity,

	String request,

	String startPoint,

	List<String> waypoints,

	String endPoint,

	LocalTime deliveryManagerStartTime,

	LocalTime deliveryManagerEndTime

) {

	public static AiPromptRequest from(OrderCreatedResult result, LocalTime startTime, LocalTime endTime) {
		return AiPromptRequest.builder()
			.productName(result.productName())
			.quantity(result.quantity())
			.request(result.request())
			.startPoint(result.startPoint())
			.waypoints(result.waypoints())
			.endPoint(result.endPoint())
			.deliveryManagerStartTime(startTime)
			.deliveryManagerEndTime(endTime)
			.build();
	}

}
