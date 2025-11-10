package com.luckylogistics.order.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "deliveryId")
public interface DeliveryFeignClient {
    @GetMapping("/{companyId}")
    boolean isDeliveryIdExists(@PathVariable("deliveryId") UUID deliveryId);
}
