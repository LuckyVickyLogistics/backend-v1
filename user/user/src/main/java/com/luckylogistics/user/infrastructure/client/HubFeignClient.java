package com.luckylogistics.user.infrastructure.client;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.luckylogistics.user.common.response.ApiResponse;

@FeignClient(name = "hub", path ="/api/v1/hubs")
public interface HubFeignClient {

	@GetMapping("{hubId}")
	ApiResponse<HubResponse> getHubById(
		//@RequestHeader("X-Internal-Request") String internalHeader,
		@PathVariable UUID hubId
	);

	@GetMapping
	ApiResponse<List<HubResponse>> getAllHubs(
		//@RequestHeader("X-Internal-Request") String internalHeader
	);

}
