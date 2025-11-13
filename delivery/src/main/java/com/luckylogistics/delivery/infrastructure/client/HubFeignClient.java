package com.luckylogistics.delivery.infrastructure.client;

import com.luckylogistics.common.infrastructure.response.ApiResponse;
import com.luckylogistics.delivery.infrastructure.client.dto.HubResponse;
import com.luckylogistics.delivery.infrastructure.client.dto.HubRoutePlanResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "hub", path = "/api/v1")
public interface HubFeignClient {

    // 허브 단건 조회
    @GetMapping("/hubs/{hubId}")
    ApiResponse<HubResponse> getHub(@PathVariable("hubId") UUID hubId);

    // 사용자 id로 담당 허브 조회
    @GetMapping("/hubs/manager/{userId}/hub")
    ApiResponse<HubResponse> getHubByUserId(@PathVariable("userId") Long userId);

    // 배송 경로 조회
    @GetMapping("/routes")
    ApiResponse<HubRoutePlanResponse> getHubRoutePlan(
            @RequestParam("from") UUID departureHubId,
            @RequestParam("to") UUID arrivalHubId
    );
}