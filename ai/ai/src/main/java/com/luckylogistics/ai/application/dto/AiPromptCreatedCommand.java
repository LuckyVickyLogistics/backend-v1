package com.luckylogistics.ai.application.dto;

import java.time.LocalTime;
import java.util.List;

import com.luckylogistics.ai.presentation.dto.AiPromptCreatedRequest;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record AiPromptCreatedCommand(

	String productName,

	int quantity,

	String request,

	String startPoint,

	List<String> waypoints,

	String endPoint,

	LocalTime deliveryManagerStartTime,

	LocalTime deliveryManagerEndTime

) {

	public static AiPromptCreatedCommand from(AiPromptCreatedRequest requestDto) {
		return AiPromptCreatedCommand.builder()
			.productName(requestDto.productName())
			.quantity(requestDto.quantity())
			.request(requestDto.request())
			.startPoint(requestDto.startPoint())
			.waypoints(requestDto.waypoints())
			.endPoint(requestDto.endPoint())
			.deliveryManagerStartTime(requestDto.deliveryManagerStartTime())
			.deliveryManagerEndTime(requestDto.deliveryManagerEndTime())
			.build();
	}

}
