package com.luckylogistics.delivery.infrastructure.client;

import com.luckylogistics.delivery.infrastructure.client.dto.HubResponse;
import com.luckylogistics.delivery.infrastructure.client.dto.HubRoutePlanResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

// TODO: Hub Service 연동
@FeignClient(name = "hub")
public interface HubFeignClient {

    /**
     * 허브 단건 조회
     */
    @GetMapping("/api/v1/hubs/{hubId}")
    HubResponse getHub(@PathVariable("hubId") UUID hubId);

    /**
     * 배송 경로 조회
     */
    @GetMapping("/api/v1/hub-routes")
    HubRoutePlanResponse getRoutePlan(
            @RequestParam("from") UUID departureHubId,
            @RequestParam("to") UUID arrivalHubId
    );
}