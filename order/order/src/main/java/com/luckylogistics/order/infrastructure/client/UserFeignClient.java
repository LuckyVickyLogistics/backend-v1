package com.luckylogistics.order.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.luckylogistics.order.common.response.ApiResponse;
import com.luckylogistics.order.infrastructure.client.dto.GetUserClientResponse;

@FeignClient(name = "user", path = "/api/v1/users")
public interface UserFeignClient {

	@GetMapping("/me")
	ApiResponse<GetUserClientResponse> getMe();

}
