package com.luckylogistics.delivery.infrastructure.repository;

import com.luckylogistics.delivery.domain.model.Delivery;
import com.luckylogistics.delivery.domain.model.DeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        LEFT JOIN FETCH d.companyDeliveryManager cdm
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

    /**
     * 마스터/업체 관리자용 검색
     */
    @Query("""
        SELECT d
          FROM Delivery d
          JOIN FETCH d.companyDeliveryManager cdm
         WHERE d.deletedAt IS NULL
           AND (:status IS NULL OR d.status = :status)
           AND (:departureHubId IS NULL OR d.departureHubId = :departureHubId)
           AND (:arrivalHubId IS NULL OR d.arrivalHubId = :arrivalHubId)
    """)
    Page<Delivery> searchDeliveries(
            @Param("status") DeliveryStatus status,
            @Param("departureHubId") UUID departureHubId,
            @Param("arrivalHubId") UUID arrivalHubId,
            Pageable pageable
    );

    /**
     * 허브 관리자용: 출발/도착/경유 허브 확인 + 필터
     * - d.departureHubId: 출발 허브
     * - d.arrivalHubId: 도착 허브
     * - EXISTS (routes): 경유 허브 (DeliveryRoute의 출발/도착 허브)
     */
    @Query("""
        SELECT DISTINCT d
          FROM Delivery d
          JOIN FETCH d.companyDeliveryManager cdm
          LEFT JOIN d.routes r
        WHERE d.deletedAt IS NULL
          AND (d.departureHubId = :hubId
             OR d.arrivalHubId = :hubId
             OR r.departureHubId = :hubId
             OR r.arrivalHubId = :hubId)
          AND (:status IS NULL OR d.status = :status)
          AND (:departureHubId IS NULL OR d.departureHubId = :departureHubId)
          AND (:arrivalHubId IS NULL OR d.arrivalHubId = :arrivalHubId)
    """)
    Page<Delivery> searchByHubIdIncludingRoutes(
            @Param("hubId") UUID hubId,
            @Param("status") DeliveryStatus status,
            @Param("departureHubId") UUID departureHubId,
            @Param("arrivalHubId") UUID arrivalHubId,
            Pageable pageable
    );

    /**
     * 배송 담당자용: 업체 배송 담당자 / 허브 배송 담당자 + 필터
     * - companyDeliveryManager: 업체 배송 담당자
     * - hubDeliveryManager: 허브 배송 담당자
     */
    @Query(value = """
        SELECT DISTINCT d
          FROM Delivery d
          JOIN FETCH d.companyDeliveryManager cdm
          LEFT JOIN d.routes r
          LEFT JOIN r.hubDeliveryManager hdm
         WHERE d.deletedAt IS NULL
           AND (cdm.deliveryManagerId = :userId OR hdm.deliveryManagerId = :userId)
           AND (:status IS NULL OR d.status = :status)
           AND (:departureHubId IS NULL OR d.departureHubId = :departureHubId)
           AND (:arrivalHubId IS NULL OR d.arrivalHubId = :arrivalHubId)
    """)
    Page<Delivery> searchByDeliveryManagerUserId(
            @Param("userId") Long userId,
            @Param("status") DeliveryStatus status,
            @Param("departureHubId") UUID departureHubId,
            @Param("arrivalHubId") UUID arrivalHubId,
            Pageable pageable
    );
}