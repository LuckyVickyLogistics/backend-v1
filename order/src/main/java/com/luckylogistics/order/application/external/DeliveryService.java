package com.luckylogistics.order.application.external;

import java.util.UUID;

import com.luckylogistics.order.application.dto.DeliveryCreateResponse;
import com.luckylogistics.order.domain.entity.Order;

public interface DeliveryService {
    //주문 생성
    // 배송 생성
    //주문 db안에 배송 id가 들어가야 한다.

    //주문 정상적으로 호출이되면, 주문 ID 를 보내서, 그걸로 배송 ID를 받고, 걔로 DB save
    //배송 생성 api() -> response로 ID가 오니까 그걸 받아서 처리

    void isDeliveryExists(UUID deliveryId);

	DeliveryCreateResponse createDelivery(Order order, UUID departureHubId, UUID arrivalHubId, String recipientName, String recipientSlackId);

}
