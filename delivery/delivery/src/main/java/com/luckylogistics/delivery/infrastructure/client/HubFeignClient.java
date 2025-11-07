package com.luckylogistics.delivery.infrastructure.client;

import com.luckylogistics.delivery.infrastructure.client.dto.HubResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// TODO: Hub Service 연동
@FeignClient(name = "hub")
public interface HubFeignClient {
    @GetMapping("/api/v1/hubs/{hubId}")
    HubResponse getHub(@PathVariable("hubId") String hubId);
}