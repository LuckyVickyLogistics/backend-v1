package com.luckylogistics.delivery.domain.repository;

import com.luckylogistics.delivery.domain.model.Delivery;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository {

    Delivery save(Delivery delivery);

    boolean existsByOrderId(UUID orderId);

    Optional<Delivery> findLastDeliveryByArrivalHubId(UUID hubId);
}