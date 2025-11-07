package com.luckylogistics.delivery.infrastructure.repository;

import com.luckylogistics.delivery.domain.model.DeliveryManager;
import com.luckylogistics.delivery.domain.model.DeliveryManagerType;
import com.luckylogistics.delivery.domain.repository.DeliveryManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryManagerRepositoryImpl implements DeliveryManagerRepository {

    private final JpaDeliveryManagerRepository jpaRepository;

    @Override
    @Transactional
    public DeliveryManager save(DeliveryManager deliveryManager) {
        return jpaRepository.save(deliveryManager);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsByDeliveryManagerIdAndDeletedAtIsNull(id);
    }

    @Override
    public Optional<DeliveryManager> findById(Long id) {
        return jpaRepository.findByDeliveryManagerIdAndDeletedAtIsNull(id);
    }

    @Override
    public Page<DeliveryManager> findByTypeAndHubId(
            DeliveryManagerType type,
            UUID hubId,
            Pageable pageable
    ) {
        return jpaRepository.findByTypeAndHubId(type, hubId, pageable);
    }

    @Override
    public Optional<Integer> findMaxSequenceByType(DeliveryManagerType type) {
        return jpaRepository.findMaxSequenceByType(type);
    }

    @Override
    public Optional<Integer> findMaxSequenceByTypeAndHubId(
            DeliveryManagerType type,
            UUID hubId
    ) {
        return jpaRepository.findMaxSequenceByTypeAndHubId(type, hubId);
    }
}