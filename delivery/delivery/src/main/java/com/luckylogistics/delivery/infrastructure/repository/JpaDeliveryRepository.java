package com.luckylogistics.delivery.infrastructure.repository;

import com.luckylogistics.delivery.domain.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaDeliveryRepository extends JpaRepository<Delivery, UUID> {

    boolean existsByOrderIdAndDeletedAtIsNull(UUID orderId);

    /**
     * 특정 허브의 마지막 배송 조회 (업체 배송 담당자 배정용)
     */
    Optional<Delivery> findTopByArrivalHubIdAndDeletedAtIsNullOrderByCreatedAtDesc(UUID hubId);
}