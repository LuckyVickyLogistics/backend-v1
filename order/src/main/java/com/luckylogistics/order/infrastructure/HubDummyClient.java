package com.luckylogistics.order.infrastructure;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.luckylogistics.common.infrastructure.response.ApiResponse;


@Component
public class HubDummyClient {

	public ApiResponse<String> getHubManager(UUID hubId) {
		return ApiResponse.success("leegeonhee0204@gmail.com", "Dummy Email");
	}
}
