package com.luckylogistics.ai.presentation.dto;

import java.time.LocalTime;
import java.util.List;

import com.luckylogistics.ai.application.dto.AiPromptCreatedCommand;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AiPromptCreatedRequest(

	@NotEmpty(message = "상품명은 필수입니다.")
	String productName,

	@Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
	int quantity,

	@NotEmpty(message = "요청사항은 필수입니다.")
	String request,

	@NotEmpty(message = "출발지는 필수입니다.")
	String startPoint,

	List<String> waypoints,

	@NotEmpty(message = "도착지는 필수입니다.")
	String endPoint,

	@NotNull(message = "배송 매니저 근무 시작 시간은 필수입니다.")
	LocalTime deliveryManagerStartTime,

	@NotNull(message = "배송 매니저 근무 종료 시간은 필수입니다.")
	LocalTime deliveryManagerEndTime

) {

	public static AiPromptCreatedCommand of(AiPromptCreatedRequest requestDto) {
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
