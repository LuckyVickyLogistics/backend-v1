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

    Optional<Delivery> findByIdWithRoutes(UUID id);

    boolean existsByOrderId(UUID orderId);

    Optional<Delivery> findLastDeliveryByArrivalHubId(UUID hubId);

    Page<Delivery> searchDeliveries(DeliveryStatus status, UUID departureHubId, UUID arrivalHubId, Pageable pageable);

    Page<Delivery> searchByHubId(UUID hubId, Pageable pageable);

    /**
     * 업체 배송 담당자가 담당하는 배송 조회
     */
    Page<Delivery> searchByCompanyDeliveryManagerUserId(Long userId, Pageable pageable);

    /**
     * 허브 배송 담당자가 담당하는 배송 조회
     */
    Page<Delivery> searchByHubDeliveryManagerUserId(Long userId, Pageable pageable);
}