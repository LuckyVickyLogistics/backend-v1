package com.luckylogistics.delivery.domain.repository;

import com.luckylogistics.delivery.domain.model.DeliveryRoute;
import com.luckylogistics.delivery.domain.model.DeliveryRouteStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRouteRepository {

    DeliveryRoute save(DeliveryRoute deliveryRoute);

    List<DeliveryRoute> findByDeliveryIdOrderBySequence(UUID deliveryId);

    Optional<DeliveryRoute> findLastDeliveryRoute();

    Optional<DeliveryRoute> findByIdWithManager(UUID deliveryRouteId);

    // 배송 담당자가 담당하는 특정 상태의 경로 조회 (페이징)
    Page<DeliveryRoute> searchByHubDeliveryManagerIdAndStatus(
            Long deliveryManagerId,
            DeliveryRouteStatus status,
            Pageable pageable
    );
}