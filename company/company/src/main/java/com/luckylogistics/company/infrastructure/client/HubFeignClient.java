package com.luckylogistics.company.infrastructure.client;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub")
public interface HubFeignClient {
    @GetMapping("/{hubId}")
    boolean isHubExists(@PathVariable("hubId") UUID hubId);

}
