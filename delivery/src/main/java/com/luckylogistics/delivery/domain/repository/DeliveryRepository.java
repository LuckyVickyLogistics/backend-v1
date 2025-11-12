package com.luckylogistics.delivery.domain.repository;

import com.luckylogistics.delivery.domain.model.Delivery;
import com.luckylogistics.delivery.domain.model.DeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository {

    Delivery save(Delivery delivery);

    Optional<Delivery> findById(UUID id);

    Optional<Delivery> findByIdWithCompanyManager(UUID id);

    Optional<Delivery> findByIdWithRoutes(UUID id);

    boolean existsByOrderId(UUID orderId);

    Optional<Delivery> findLastDeliveryByArrivalHubId(UUID hubId);

    Page<Delivery> searchDeliveries(DeliveryStatus status, UUID departureHubId, UUID arrivalHubId, Pageable pageable);

    // 허브 관리자용: 배송 경로에 해당 허브가 포함된 배송 검색 + 필터
    //(출발/도착/경유 허브 모두 포함)
    Page<Delivery> searchByHubIdIncludingRoutes(
            UUID hubId,
            DeliveryStatus status,
            UUID departureHubId,
            UUID arrivalHubId,
            Pageable pageable
    );

    // 배송 담당자용: 업체 배송 담당자 / 허브 배송 담당자 검색 + 필터
    Page<Delivery> searchByDeliveryManagerUserId(
            Long userId,
            DeliveryStatus status,
            UUID departureHubId,
            UUID arrivalHubId,
            Pageable pageable
    );
}