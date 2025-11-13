package com.luckylogistics.order.infrastructure.client;

import java.util.UUID;

import com.luckylogistics.order.infrastructure.client.dto.HubResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.luckylogistics.common.infrastructure.response.ApiResponse;

@FeignClient(name = "hub")
public interface HubFeignClient {
    @GetMapping("/api/v1/hubs/{hubId}")
    ApiResponse<HubResponse> getHub(@PathVariable("hubId") UUID hubId);

    @GetMapping("/api/v1/hubs/manager/{userId}/hub")
    ApiResponse<HubResponse> getHubByUserId(@PathVariable("userid")Long userId);

	@GetMapping("/api/v1/hubs/manager/{hubId}/slack")
	ApiResponse<String> getHubManager(@PathVariable("hubId") UUID hubId);


}
