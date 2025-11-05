package com.luckylogistics.delivery.infrastructure.repository;

import com.luckylogistics.delivery.domain.repository.DeliveryManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryManagerRepositoryImpl implements DeliveryManagerRepository {

    private final JpaDeliveryManagerRepository jpaRepository;

}