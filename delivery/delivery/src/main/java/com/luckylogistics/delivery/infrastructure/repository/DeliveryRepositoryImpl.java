package com.luckylogistics.delivery.infrastructure.repository;

import com.luckylogistics.delivery.domain.model.Delivery;
import com.luckylogistics.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryRepositoryImpl implements DeliveryRepository {

    private final JpaDeliveryRepository jpaRepository;

    @Override
    @Transactional
    public Delivery save(Delivery delivery) {
        return jpaRepository.save(delivery);
    }

    @Override
    public Optional<Delivery> findById(UUID id) {
        return jpaRepository.findByDeliveryIdAndDeletedAtIsNull(id);
    }

    @Override
    public Optional<Delivery> findByIdWithRoutes(UUID id) {
        return jpaRepository.findByIdWithRoutes(id);
    }

    @Override
    public boolean existsByOrderId(UUID orderId) {
        return jpaRepository.existsByOrderIdAndDeletedAtIsNull(orderId);
    }

    @Override
    public Optional<Delivery> findLastDeliveryByArrivalHubId(UUID hubId) {
        return jpaRepository.findTopByArrivalHubIdAndDeletedAtIsNullOrderByCreatedAtDesc(hubId);
    }
}