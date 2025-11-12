package com.luckylogistics.delivery.infrastructure.client;

import com.luckylogistics.delivery.application.service.OrderService;
import com.luckylogistics.delivery.infrastructure.client.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
class OrderAdapter implements OrderService {

     private final OrderFeignClient orderClient;

    // 주문 존재 여부 검증
    @Override
    public void validateOrderExists(UUID orderId) {
        OrderResponse response = orderClient.getOrder(orderId).data();
        response.validate();
        log.debug("[OrderClient] 주문 존재 확인 완료. orderId: {}", response.orderId());
    }
}