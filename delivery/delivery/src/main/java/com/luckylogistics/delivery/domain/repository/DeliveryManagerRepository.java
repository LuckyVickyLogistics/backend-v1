package com.luckylogistics.delivery.domain.repository;

import com.luckylogistics.delivery.domain.model.DeliveryManager;
import com.luckylogistics.delivery.domain.model.DeliveryManagerType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryManagerRepository {

    DeliveryManager save(DeliveryManager deliveryManager);

    boolean existsById(Long id);

    Optional<DeliveryManager> findById(Long id);

    Page<DeliveryManager> findByTypeAndHubId(DeliveryManagerType type, UUID hubId, Pageable pageable);

    Optional<Integer> findMaxSequenceByType(DeliveryManagerType type);

    Optional<Integer> findMaxSequenceByTypeAndHubId(
            DeliveryManagerType type,
            UUID hubId
    );
}