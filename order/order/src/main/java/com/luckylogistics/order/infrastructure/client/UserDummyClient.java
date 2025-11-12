package com.luckylogistics.order.infrastructure.client;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.luckylogistics.order.common.response.ApiResponse;
import com.luckylogistics.order.infrastructure.client.dto.GetUserClientResponse;

@Component
public class UserDummyClient {
	public ApiResponse<GetUserClientResponse> getMe() {
		GetUserClientResponse dummy = new GetUserClientResponse(
			1L,
			UUID.randomUUID(),
			"수령인",
			"test@naver.com",
			GetUserClientResponse.UserRole.MASTER_ADMIN,
			GetUserClientResponse.OrganizationType.COMPANY,
			UUID.randomUUID(),
			GetUserClientResponse.Status.APPROVED,
			false,
			LocalDateTime.now().minusDays(10),
			LocalDateTime.now()
		);

		return ApiResponse.success(dummy, "Dummy User Data");
	}

}
