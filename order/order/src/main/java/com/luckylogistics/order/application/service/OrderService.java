package com.luckylogistics.order.application.service;

import java.util.UUID;

import com.luckylogistics.order.application.dto.OrderRequest;
import com.luckylogistics.order.application.dto.OrderUpdateRequest;
import com.luckylogistics.order.application.dto.OrderUpdateResponse;
import com.luckylogistics.order.common.exception.BusinessException;
import com.luckylogistics.order.common.exception.ExceptionCode;
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

    //update
    @Transactional
    public OrderUpdateResponse updateOrder(UUID orderId, OrderUpdateRequest orderUpdateRequest) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow( () -> new BusinessException(ExceptionCode.ORDER_ID_ERROR));

        order.update(orderUpdateRequest.Quantity(), orderUpdateRequest.request());

        return new OrderUpdateResponse(
                order.getOrderId(),
                order.getQuantity(),
                order.getRequest()
        );

    }

    //delete
    public void deleteOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow( () -> new BusinessException(ExceptionCode.ORDER_ID_ERROR));

        order.delete();
    }

    //rollback
    public void rollbackDeleteOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow( () -> new BusinessException(ExceptionCode.ORDER_ID_ERROR));

        order.rollbackDelete();
    }
}
