package com.luckylogistics.order.infrastructure.client.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import com.luckylogistics.order.application.dto.DeliveryCreateResponse;

public record CreateDeliveryClientResponse(
	UUID deliveryId,
	UUID orderId,
	DeliveryStatus status,
	UUID departureHubId,
	UUID arrivalHubId,
	String deliveryAddress,
	String recipientName,
	String recipientSlackId,
	Long companyDeliveryManagerId, // 배송 담당자 ID
	LocalDateTime createdAt,
	List<DeliveryRouteResponse> routes,
	LocalTime workStartTime,
	LocalTime workEndTime
	// 배송 담당자 이름은 유저 서비스에서 가져옴
	// 배송 담당자 슬랙 이메일은 유저 서비스에서 가져옴
) {

	public enum DeliveryStatus {
		HUB_WAITING,
		HUB_MOVING,
		HUB_ARRIVED,
		COMPANY_MOVING,
		COMPLETED
	}

	public record DeliveryRouteResponse(
		UUID deliveryRouteId,
		Integer sequence,
		UUID departureHubId,
		UUID arrivalHubId,
		BigDecimal estimatedDistance,
		Integer estimatedDuration,
		BigDecimal actualDistance,
		Integer actualDuration,
		DeliveryStatus status,
		Long hubDeliveryManagerId,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
		// 허브 이름
	) {}

	public static DeliveryCreateResponse of(CreateDeliveryClientResponse response) {
		List<CreateDeliveryClientResponse.DeliveryRouteResponse> routes =
			response.routes() == null ? List.of() : List.copyOf(response.routes());

		routes = new ArrayList<>(routes);
		routes.sort(Comparator.comparingInt(CreateDeliveryClientResponse.DeliveryRouteResponse::sequence));

		String startPoint = routes.get(0).departureHubId().toString(); // 임시로 UUID를 허브 아이디로 저장. 허브 서비스에서 받아와야함

		List<String> waypoints = routes.size() > 2
			? routes.subList(1, routes.size() - 1)
			.stream()
			.map(r -> r.departureHubId().toString())
			.toList()
			: List.of();

		String endPoint = routes.get(routes.size() - 1).arrivalHubId().toString();

		return DeliveryCreateResponse.builder()
			.deliveryId(response.deliveryId)
			.startPoint(startPoint)
			.waypoints(waypoints)
			.endPoint(endPoint)
			.deliveryManagerName("김배달")
			.deliveryManagerSlackId("kimdelivery@naver.com")
			.deliveryManagerWorkingStartTime(response.workStartTime)
			.deliveryManagerWorkingEndTime(response.workEndTime)
			.build();
	}

}
