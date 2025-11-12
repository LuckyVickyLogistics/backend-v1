package com.luckylogistics.ai.application.dto;

import java.time.LocalTime;
import java.util.List;

import lombok.Builder;

@Builder
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
}
