package com.luckylogistics.order.infrastructure.client;

import com.luckylogistics.order.application.dto.MinusRequest;
import com.luckylogistics.order.application.dto.PlusRequest;
import com.luckylogistics.order.application.external.DeliveryService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "delivery")
public interface DeliveryFeignClient {
    @GetMapping("/{companyId}")
    boolean isDeliveryIdExists(@PathVariable("deliveryId") UUID deliveryId);

}
