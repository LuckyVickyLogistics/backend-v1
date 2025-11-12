package com.luckylogistics.delivery.infrastructure.repository;

import com.luckylogistics.delivery.domain.model.DeliveryRoute;
import com.luckylogistics.delivery.domain.repository.DeliveryRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryRouteRepositoryImpl implements DeliveryRouteRepository {

    private final JpaDeliveryRouteRepository jpaRepository;

    @Override
    @Transactional
    public DeliveryRoute save(DeliveryRoute deliveryRoute) {
        return jpaRepository.save(deliveryRoute);
    }

    @Override
    public List<DeliveryRoute> findByDeliveryIdOrderBySequence(UUID deliveryId) {
        return jpaRepository.findByDeliveryIdOrderBySequence(deliveryId);
    }

    @Override
    public Optional<DeliveryRoute> findLastDeliveryRoute() {
        return jpaRepository.findTopByDeletedAtIsNullOrderByCreatedAtDesc();
    }

    @Override
    public Optional<DeliveryRoute> findByIdWithManager(UUID deliveryRouteId) {
        return jpaRepository.findByIdWithManager(deliveryRouteId);
    }
}