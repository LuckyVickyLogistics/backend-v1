package com.luckylogistics.product.infrastructure.client;

import com.luckylogistics.product.infrastructure.client.dto.HubResponse;
import com.luckylogistics.product.common.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "hub",path = "/api/v1/hubs")
public interface HubFeignClient {
    @GetMapping("/{hubId}")
   ApiResponse<HubResponse> getHub(@PathVariable("hubId") UUID hubId);

    @GetMapping("/manager/{userId}/hub")
   ApiResponse<HubResponse> getHubByUserId(Long userId);
}
