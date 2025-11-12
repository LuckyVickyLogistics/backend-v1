package com.luckylogistics.user.infrastructure.client;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.luckylogistics.user.common.response.ApiResponse;

@FeignClient(name = "hub")
public interface HubClient {

	@GetMapping("/api/v1/hubs/{hubId}")
	ApiResponse<HubResponse> getHubById(
		@RequestHeader("X-Internal-Request") String internalHeader,
		@PathVariable UUID hubId
	);

	@GetMapping("/api/v1/hubs")
	ApiResponse<List<HubResponse>> getAllHubs(
		@RequestHeader("X-Internal-Request") String internalHeader
	);

}
