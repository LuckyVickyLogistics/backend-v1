package com.luckylogistics.product.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "hub")
public interface HubFeignClient {
    @GetMapping("/{hubId}")
    boolean isHubExists(@PathVariable("hubId") UUID hubId);
}
