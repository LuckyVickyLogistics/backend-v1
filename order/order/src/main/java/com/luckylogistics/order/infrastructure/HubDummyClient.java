package com.luckylogistics.order.infrastructure;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.luckylogistics.order.common.response.ApiResponse;
import com.luckylogistics.order.infrastructure.client.dto.GetHubManagerClientResponse;

@Component
public class HubDummyClient {

	public ApiResponse<GetHubManagerClientResponse> getHubManager(UUID hubId) {
		GetHubManagerClientResponse dummy = new GetHubManagerClientResponse(
			"leegeonhee0204@gmail.com"
		);

		return ApiResponse.success(dummy, "Dummy Email");
	}
}
