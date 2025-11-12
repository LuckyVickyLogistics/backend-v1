package com.luckylogistics.delivery.infrastructure.client;

import com.luckylogistics.delivery.common.response.ApiResponse;
import com.luckylogistics.delivery.infrastructure.client.dto.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "order", path = "/api/v1/orders")
public interface OrderFeignClient {

    @GetMapping("/{orderId}")
    ApiResponse<OrderResponse> getOrder(@PathVariable("orderId") UUID orderId);
}