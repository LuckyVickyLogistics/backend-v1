package com.luckylogistics.delivery.infrastructure.repository;

import com.luckylogistics.delivery.domain.model.DeliveryManager;
import com.luckylogistics.delivery.domain.model.DeliveryManagerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface JpaDeliveryManagerRepository extends JpaRepository<DeliveryManager, Long> {

    boolean existsByDeliveryManagerIdAndDeletedAtIsNull(Long id);

    @Query("""
        SELECT MAX(dm.deliverySequence)
        FROM DeliveryManager dm
        WHERE dm.type = :type AND dm.deletedAt IS NULL
    """)
    Optional<Integer> findMaxSequenceByType(@Param("type") DeliveryManagerType type);

    @Query("""
        SELECT MAX(dm.deliverySequence)
        FROM DeliveryManager dm
        WHERE dm.type = :type
          AND dm.hubId.hubId = :hubId
          AND dm.deletedAt IS NULL
    """)
    Optional<Integer> findMaxSequenceByTypeAndHubId(
            @Param("type") DeliveryManagerType type,
            @Param("hubId") UUID hubId
    );
}