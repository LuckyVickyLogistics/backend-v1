package com.luckylogistics.delivery.infrastructure.repository;

import com.luckylogistics.delivery.domain.model.DeliveryRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaDeliveryRouteRepository extends JpaRepository<DeliveryRoute, UUID> {

    @Query("""
        SELECT dr FROM DeliveryRoute dr
        WHERE dr.delivery.deliveryId = :deliveryId
          AND dr.deletedAt IS NULL
        ORDER BY dr.sequence
    """)
    List<DeliveryRoute> findByDeliveryIdOrderBySequence(@Param("deliveryId") UUID deliveryId);

    /**
     * 마지막 배송 경로 조회 (허브 배송 담당자 배정용)
     */
    Optional<DeliveryRoute> findTopByDeletedAtIsNullOrderByCreatedAtDesc();
}