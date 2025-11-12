package com.luckylogistics.user.infrastructure.client;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.luckylogistics.user.common.response.ApiResponse;

@Component
public class HubDummyClient {

	public boolean isHubExists(UUID hubId) {
		return true; // 실제 연동 전에는 무조건 존재한다고 처리
	}

	public ApiResponse<HubResponse> getHubById(UUID hubId) {
		HubResponse dummy = new HubResponse(
			hubId,
			"Dummy Hub",
			"Dummy Hub Address",
			37.5665,
			126.9780,
			null,
			null
		);

		return ApiResponse.success(dummy, "Dummy hub data");
	}

	public ApiResponse<List<HubResponse>> getAllHubs() {
		HubResponse dummyA = new HubResponse(
			UUID.randomUUID(),
			"Dummy Hub A",
			"Dummy Hub Address A",
			37.5665,
			126.9780,
			null,
			null
		);

		HubResponse dummyB = new HubResponse(
			UUID.randomUUID(),
			"Dummy Hub B",
			"Dummy Hub Address B",
			37.5665,
			126.9780,
			null,
			null
		);

		return ApiResponse.success(List.of(dummyA, dummyB), "Dummy hub list");
	}
}
