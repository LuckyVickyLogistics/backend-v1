package com.luckylogistics.order.application.service;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luckylogistics.order.domain.entity.Order;
import com.luckylogistics.order.domain.repository.OrderRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

	private final OrderRepository orderRepository;

	@Transactional
	public void createOrder() {
		Order order = Order.create(1, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "REQUEST");
		orderRepository.save(order);
	}

}
