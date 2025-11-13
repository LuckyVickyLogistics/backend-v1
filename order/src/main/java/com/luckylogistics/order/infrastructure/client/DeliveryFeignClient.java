package com.luckylogistics.order.infrastructure.client;

import com.luckylogistics.common.infrastructure.response.ApiResponse;
import com.luckylogistics.order.infrastructure.client.dto.CreateDeliveryClientRequest;
import com.luckylogistics.order.infrastructure.client.dto.CreateDeliveryClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "delivery")
public interface DeliveryFeignClient {
    //단건조회 메소드로 이름 바꿀 필요 있음
    @GetMapping("/api/v1/deliveries/{deliveryId}")
    boolean getDelivery(@PathVariable("deliveryId") UUID deliveryId);

    @PostMapping
    ApiResponse<CreateDeliveryClientResponse> createDelivery(@RequestBody CreateDeliveryClientRequest requestDto);
}
