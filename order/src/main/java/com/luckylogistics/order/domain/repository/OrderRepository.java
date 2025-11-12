package com.luckylogistics.order.domain.repository;

import com.luckylogistics.order.domain.entity.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Order save(Order product);
    Optional<Order> findById(UUID id);
    List<Order> findAll();
}
