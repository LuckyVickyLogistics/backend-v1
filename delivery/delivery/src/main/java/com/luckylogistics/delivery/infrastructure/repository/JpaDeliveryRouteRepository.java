package com.luckylogistics.delivery.infrastructure.repository;

import com.luckylogistics.delivery.domain.model.DeliveryRoute;
import com.luckylogistics.delivery.domain.model.DeliveryRouteStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaDeliveryRouteRepository extends JpaRepository<DeliveryRoute, UUID> {

    @Query("""
        SELECT dr FROM DeliveryRoute dr
        WHERE dr.deliveryId = :deliveryId
          AND dr.deletedAt IS NULL
        ORDER BY dr.sequence
    """)
    List<DeliveryRoute> findByDeliveryIdOrderBySequence(@Param("deliveryId") UUID deliveryId);

    /**
     * 마지막 배송 경로 조회 (허브 배송 담당자 배정용)
     */
    Optional<DeliveryRoute> findTopByDeletedAtIsNullOrderByCreatedAtDesc();

    @Query("""
        SELECT dr FROM DeliveryRoute dr
        JOIN FETCH dr.hubDeliveryManager
        WHERE dr.deliveryRouteId = :id
        AND dr.deletedAt IS NULL
    """)
    Optional<DeliveryRoute> findByIdWithManager(@Param("id") UUID id);

    /**
     * 배송 담당자가 담당하는 특정 상태의 경로 조회 (페이징)
     */
    @Query("""
        SELECT dr
        FROM DeliveryRoute dr
        JOIN FETCH dr.hubDeliveryManager hdm
        WHERE (:deliveryManagerId IS NULL OR hdm.deliveryManagerId = :deliveryManagerId)
        AND (:status IS NULL OR dr.status = :status)
        AND dr.deletedAt IS NULL
        ORDER BY dr.createdAt DESC        
        """)
    Page<DeliveryRoute> searchByHubDeliveryManagerIdAndStatus(
            @Param("deliveryManagerId") Long deliveryManagerId,
            @Param("status") DeliveryRouteStatus status,
            Pageable pageable
    );
}