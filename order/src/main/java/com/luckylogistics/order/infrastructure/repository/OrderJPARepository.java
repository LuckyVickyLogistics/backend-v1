package com.luckylogistics.order.infrastructure.repository;

import com.luckylogistics.order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJPARepository extends JpaRepository<Order, UUID> {
}
