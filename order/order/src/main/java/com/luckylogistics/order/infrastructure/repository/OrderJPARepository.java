package com.luckylogistics.order.infrastructure.repository;

import com.luckylogistics.order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderJPARepository extends JpaRepository<Order, UUID> {


    // 단건 조회 제한 (product와 다르게 companyId와 hubId를 주지 않으므로, userId와 createdAt을 비교하는게 제일 깔끔하다..!)
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
