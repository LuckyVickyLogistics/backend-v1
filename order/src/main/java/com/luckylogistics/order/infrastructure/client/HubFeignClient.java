package com.luckylogistics.order.infrastructure.client;

import java.util.UUID;

import com.luckylogistics.order.infrastructure.client.dto.HubResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.luckylogistics.order.common.response.ApiResponse;

@FeignClient(name = "hub", path = "/api/v1/hubs")
public interface HubFeignClient {
    @GetMapping("/{hubId}")
    ApiResponse<HubResponse> getHub(@PathVariable("hubId") UUID hubId);

    @GetMapping("/manager/{userId}/hub")
    ApiResponse<HubResponse> getHubByUserId(@PathVariable("userid")Long userId);

	@GetMapping("/manager/{hubId}/slack")
	ApiResponse<String> getHubManager(@PathVariable("hubId") UUID hubId);


}
