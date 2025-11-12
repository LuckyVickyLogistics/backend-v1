package com.luckylogistics.order.infrastructure.client;

import com.luckylogistics.order.application.dto.MinusRequest;
import com.luckylogistics.order.application.dto.PlusRequest;
import com.luckylogistics.order.application.external.DeliveryService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "delivery", path = "/api/v1/deliveries")
public interface DeliveryFeignClient {
    //단건조회 메소드로 이름 바꿀 필요 있음
    @GetMapping("/{deliveryId}")
    boolean getDelivery(@PathVariable("deliveryId") UUID deliveryId);
}
