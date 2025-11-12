package com.luckylogistics.order.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.luckylogistics.order.domain.entity.Order;
import com.luckylogistics.order.domain.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

	private final OrderJPARepository orderJPARepository;

	@Override
	public Order save(Order product) {
		return orderJPARepository.save(product);
	}

	@Override
	public Optional<Order> findById(UUID id) {
		return orderJPARepository.findById(id);
	}

	@Override
	public List<Order> findAll() {
		return orderJPARepository.findAll();
	}

    @Override
    public Optional<Order> findByOrderIdAndDeliveryId(UUID orderId, UUID deliveryId) {
        return orderJPARepository.findByOrderIdAndDeliveryId(orderId, deliveryId);
    }

    @Override
    public Optional<Order> findByOrderIdAndCreatedBy(UUID orderId, String createdBy) {
        return orderJPARepository.findByOrderIdAndCreatedBy(orderId, createdBy);
    }

    @Override
    public List<Order> findAllByDeliveryId(UUID deliveryId) {
        return orderJPARepository.findAllByDeliveryId(deliveryId);
    }

    @Override
    public List<Order> findAllByCreatedBy(String createdBy) {
        return orderJPARepository.findAllByCreatedBy(createdBy);
    }
}
