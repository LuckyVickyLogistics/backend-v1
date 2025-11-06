package com.luckylogistics.delivery.infrastructure.repository;

import com.luckylogistics.delivery.domain.model.DeliveryManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDeliveryManagerRepository extends JpaRepository<DeliveryManager, Long> {
}