package com.luckylogistics.order.infrastructure.client;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.luckylogistics.common.infrastructure.response.ApiResponse;
import com.luckylogistics.order.infrastructure.client.dto.CreateDeliveryClientRequest;
import com.luckylogistics.order.infrastructure.client.dto.CreateDeliveryClientResponse;

@Component
public class DeliveryDummyClient {
    public boolean isDelieveryExists(UUID delieveryId) {
        return true;
    }

	public ApiResponse<CreateDeliveryClientResponse> createDelivery(CreateDeliveryClientRequest requestDto) {
		CreateDeliveryClientResponse.DeliveryRouteResponse route1 =
			new CreateDeliveryClientResponse.DeliveryRouteResponse(
				UUID.randomUUID(),
				1,
				UUID.randomUUID(),
				UUID.randomUUID(),
				new BigDecimal("12.5"),
				40,
				null,
				null,
				CreateDeliveryClientResponse.DeliveryStatus.HUB_WAITING,
				1001L,
				LocalDateTime.now(),
				LocalDateTime.now()
			);

		CreateDeliveryClientResponse.DeliveryRouteResponse route2 =
			new CreateDeliveryClientResponse.DeliveryRouteResponse(
				UUID.randomUUID(),
				2,
				route1.arrivalHubId(),
				UUID.randomUUID(),
				new BigDecimal("7.8"),
				25,
				null,
				null,
				CreateDeliveryClientResponse.DeliveryStatus.HUB_WAITING,
				1002L,
				LocalDateTime.now(),
				LocalDateTime.now()
			);

		CreateDeliveryClientResponse dummy = new CreateDeliveryClientResponse(
			UUID.randomUUID(),
			requestDto.orderId(),
			CreateDeliveryClientResponse.DeliveryStatus.HUB_WAITING,
			route1.departureHubId(),
			route2.arrivalHubId(),
			"서울특별시 강남구 테헤란로 123",
			"홍길동",
			"U123456",
			2001L,
			LocalDateTime.now(),
			List.of(route1, route2),
			LocalTime.of(9, 0),
			LocalTime.of(18, 0)
		);

		return ApiResponse.success(dummy, "Dummy Delivery Data");
	}

}
