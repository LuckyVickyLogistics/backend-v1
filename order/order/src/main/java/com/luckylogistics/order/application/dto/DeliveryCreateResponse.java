package com.luckylogistics.order.application.dto;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import lombok.Builder;

@Builder
public record DeliveryCreateResponse(

	UUID deliveryId,
	String startPoint, // 허브 이름
	List<String> waypoints, // 허브 이름
	String endPoint, // 허브 이름
	String deliveryManagerName, // 배송 담당자 이름
	String deliveryManagerSlackId, // 배송 담당자 슬랙 이메일
	LocalTime deliveryManagerWorkingStartTime, // 배송 담당자 근무 시작 시간
	LocalTime deliveryManagerWorkingEndTime // 배송 담당자 근무 마감 시간

) {
}
