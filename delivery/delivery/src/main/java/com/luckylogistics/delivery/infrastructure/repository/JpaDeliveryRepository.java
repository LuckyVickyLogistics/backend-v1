package com.luckylogistics.delivery.infrastructure.repository;

import com.luckylogistics.delivery.domain.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface JpaDeliveryRepository extends JpaRepository<Delivery, UUID> {

    Optional<Delivery> findByDeliveryIdAndDeletedAtIsNull(UUID id);

    /**
     * 배송과 배송경로를 함께 조회
     */
    @Query("""
        SELECT DISTINCT d
        FROM Delivery d
        LEFT JOIN FETCH d.routes r
        LEFT JOIN FETCH r.hubDeliveryManager m
        WHERE d.deliveryId = :id
          AND d.deletedAt IS NULL
        ORDER BY r.sequence
    """)
    Optional<Delivery> findByIdWithRoutes(@Param("id") UUID id);

    boolean existsByOrderIdAndDeletedAtIsNull(UUID orderId);

    /**
     * 특정 허브의 마지막 배송 조회 (업체 배송 담당자 배정용)
     */
    Optional<Delivery> findTopByArrivalHubIdAndDeletedAtIsNullOrderByCreatedAtDesc(UUID hubId);
}