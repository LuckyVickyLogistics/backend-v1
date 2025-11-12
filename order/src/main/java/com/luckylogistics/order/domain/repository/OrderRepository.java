package com.luckylogistics.order.domain.repository;

import com.luckylogistics.order.domain.entity.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Order save(Order product);
    Optional<Order> findById(UUID id);
    List<Order> findAll();

    // 단건 조회 제한
    // 허브 제한(= 담당 허브)
    Optional<Order> findByOrderIdAndDeliveryId(UUID orderId, UUID deliveryId);

    // 본인(작성자) 제한
    Optional<Order> findByOrderIdAndCreatedBy(UUID orderId, String createdBy);


    // 목록 조회
    // 허브 제한
    List<Order> findAllByDeliveryId(UUID deliveryId);

    // 본인(작성자) 제한
    List<Order> findAllByCreatedBy(String createdBy);
}
