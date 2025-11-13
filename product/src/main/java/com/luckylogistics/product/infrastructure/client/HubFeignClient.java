package com.luckylogistics.product.infrastructure.client;

import com.luckylogistics.product.infrastructure.client.dto.HubResponse;
import com.luckylogistics.common.infrastructure.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "hub")
public interface HubFeignClient {
    @GetMapping("/api/v1/hubs/{hubId}")
   ApiResponse<HubResponse> getHub(@PathVariable("hubId") UUID hubId);

    @GetMapping("/api/v1/hubs/manager/{userId}/hub")
   ApiResponse<HubResponse> getHubByUserId(Long userId);
}
