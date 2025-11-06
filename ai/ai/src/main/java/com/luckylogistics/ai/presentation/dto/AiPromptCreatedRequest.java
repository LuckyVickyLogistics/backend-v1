package com.luckylogistics.ai.presentation.dto;

import java.time.LocalTime;
import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AiPromptCreatedRequest(

	@NotEmpty
	String productName,

	@Min(value = 1)
	int quantity,

	@NotEmpty
	String request,

	@NotEmpty
	String startPoint,

	List<String> waypoints,

	@NotEmpty
	String endPoint,

	@NotNull
	LocalTime deliveryManagerStartTime,

	@NotNull
	LocalTime deliveryManagerEndTime

) {
}
