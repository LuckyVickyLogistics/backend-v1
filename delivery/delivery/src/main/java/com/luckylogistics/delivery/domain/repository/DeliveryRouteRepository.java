package com.luckylogistics.delivery.domain.repository;

import com.luckylogistics.delivery.domain.model.DeliveryRoute;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRouteRepository {

    DeliveryRoute save(DeliveryRoute deliveryRoute);

    List<DeliveryRoute> findByDeliveryIdOrderBySequence(UUID deliveryId);

    Optional<DeliveryRoute> findLastDeliveryRoute();

    Optional<DeliveryRoute> findByIdWithManager(UUID deliveryRouteId);
}