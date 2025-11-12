package com.luckylogistics.order.infrastructure.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.luckylogistics.order.common.response.ApiResponse;
import com.luckylogistics.order.infrastructure.client.dto.GetHubManagerClientResponse;

@FeignClient(name = "hub", path = "/api/v1/hubs")
public interface HubFeignClient {

	@GetMapping("/hub-manager/{hubId}")
	ApiResponse<GetHubManagerClientResponse> getHubManager(@PathVariable("hubId") UUID hubId);

}
