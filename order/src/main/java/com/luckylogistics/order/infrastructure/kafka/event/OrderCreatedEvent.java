package com.luckylogistics.order.infrastructure.kafka.event;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import lombok.Builder;

@Builder
public record OrderCreatedEvent(

	UUID orderId, // 주문 id

	String customerName, // 수령인 이름

	String customerEmail, // 수령인 이메일

	Instant orderedAt, // 주문 시간

	String receiverEmail, // 메시지 수신자 이메일

	String productName, // 상품 명

	int quantity, // 상품 갯수

	String request, // 요청 사항

	String startPoint, // 출발지

	List<String> waypoints, // 경유지

	String endPoint, // 목적지

	String deliveryManagerName, // 배송 담당자 이름

	String deliveryManagerEmail, // 배송 담당자 이메일

	LocalTime deliveryManagerStartTime, // 배송 담당자 근무 시작 시간

	LocalTime deliveryManagerEndTime, // 배송 담당자 근무 마감 시간

	Instant occurredAt // 이벤트 발행 시간

) {
}