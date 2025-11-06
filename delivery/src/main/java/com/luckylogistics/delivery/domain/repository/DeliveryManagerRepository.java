package com.luckylogistics.delivery.domain.repository;

import com.luckylogistics.delivery.domain.model.DeliveryManager;
import com.luckylogistics.delivery.domain.model.DeliveryManagerType;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryManagerRepository {

    DeliveryManager save(DeliveryManager deliveryManager);

    boolean existsById(Long id);

    Optional<Integer> findMaxSequenceByType(DeliveryManagerType type);

    Optional<Integer> findMaxSequenceByTypeAndHubId(
            DeliveryManagerType type,
            UUID hubId
    );
}