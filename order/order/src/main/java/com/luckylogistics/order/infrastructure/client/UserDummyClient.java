package com.luckylogistics.order.infrastructure.client;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.luckylogistics.order.common.response.ApiResponse;
import com.luckylogistics.order.infrastructure.client.dto.GetUserClientResponse;

@Component
public class UserDummyClient {
	public ApiResponse<GetUserClientResponse> getMe() {
		GetUserClientResponse dummy = new GetUserClientResponse(
			"수령인",
			"test@naver.com",
			UUID.randomUUID()
		);

		return ApiResponse.success(dummy, "Dummy User Data");
	}
}
