package com.luckylogistics.order.infrastructure.client.dto;

import java.util.UUID;

import com.luckylogistics.order.domain.entity.Order;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CreateDeliveryClientRequest(
	UUID orderId, // 주문 ID

	UUID departureHubId, // 출발 허브 ID == 공급 업체 담당 허브 ID --> 상품 조회 시 받아옴

	UUID arrivalHubId, // 도착 허브 ID == 수령 업체 담당 허브 ID --> 주문자가 속한 업체 ID (사용자 서비스) --> 업체 담당 허브 ID (업체 서비스)

	String deliveryAddress, // 배송 주소

	String recipientName, // 수령인 이름

	String recipientSlackId // 수령인 슬랙 아이디

) {

	public static CreateDeliveryClientRequest from(Order order, UUID departureHubId, UUID arrivalHubId, String recipientName, String recipientSlackId) {
		return CreateDeliveryClientRequest.builder()
			.orderId(order.getOrderId())
			.departureHubId(departureHubId)
			.arrivalHubId(arrivalHubId)
			.deliveryAddress(order.getDeliveryAddress())
			.recipientName(recipientName)
			.recipientSlackId(recipientSlackId)
			.build();
	}

}
