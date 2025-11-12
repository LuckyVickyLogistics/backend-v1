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

    // TODO: 외부 서비스 클라이언트 주입
    // private final OrderFeignClient orderClient;

    /**
     * 주문 존재 여부 검증
     */
    @Override
    public void validateOrderExists(UUID orderId) {
        // TODO: Order Service 연동
        // OrderResponse response = orderClient.getOrder(orderId);
        // FIXME: 임시 하드코딩
        OrderResponse response = OrderResponse.of(
                orderId,
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                UUID.fromString("22222222-2222-2222-2222-222222222222")
        );
        response.validate();

        log.debug("[Order] 주문 존재 확인 완료. orderId: {}, supplierCompanyId: {}, customerCompanyId: {}",
                response.orderId(), response.supplierCompanyId(), response.customerCompanyId());

        log.warn("[TODO] Order Service 연동 필요 - 임시 하드코딩 응답 반환");
    }
}