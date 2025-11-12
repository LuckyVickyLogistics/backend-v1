package com.luckylogistics.order.infrastructure.exteranal;

import org.springframework.stereotype.Component;

import com.luckylogistics.order.application.dto.UserResponse;
import com.luckylogistics.order.application.external.UserService;
import com.luckylogistics.order.common.response.ApiResponse;
import com.luckylogistics.order.infrastructure.client.UserDummyClient;
import com.luckylogistics.order.infrastructure.client.dto.GetUserClientResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserServiceAdapter implements UserService {

	// private final UserFeignClient userFeignClient;
	private final UserDummyClient userFeignClient;

	@Override
	public UserResponse getUserCompany() {
		ApiResponse<GetUserClientResponse> response = userFeignClient.getMe();
		return GetUserClientResponse.of(response.data());
	}
}
