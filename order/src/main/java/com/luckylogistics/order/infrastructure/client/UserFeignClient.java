package com.luckylogistics.order.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.luckylogistics.common.infrastructure.response.ApiResponse;
import com.luckylogistics.order.infrastructure.client.dto.GetUserClientResponse;

@FeignClient(name = "user")
public interface UserFeignClient {

	@GetMapping("/api/v1/users/me")
	ApiResponse<GetUserClientResponse> getMe();

}
