package com.luckylogistics.order.application.service;

import java.util.List;
import java.util.UUID;

import com.luckylogistics.order.application.dto.*;
import com.luckylogistics.order.application.external.ProductService;
import com.luckylogistics.order.common.exception.BusinessException;
import com.luckylogistics.order.common.exception.ExceptionCode;
import com.luckylogistics.order.domain.entity.OrderStatus;
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
    private final ProductService productService;

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

        //주문 변경 시 재고에 변경을 감지해야 함
//        int before = order.getQuantity();
//        int after = orderUpdateRequest.Quantity();
//        int difference = after - before;
//        //수량이 증가할 경우 그 만큼 재고를 감소시키고, 아니라면 추가한다
//        if (difference > 0) {
//            productService.minusProduct(order.getProductId(),new MinusRequest(difference));
//        } else if (difference < 0) {
//            //절댓값 사용해야 함 (0보다 작으면 빠지니까)
//            productService.plusProduct(order.getProductId(),new PlusRequest(Math.abs(difference)));
//        }
        //order.update(after,orderUpdateRequest.request());
        order.update(order.getQuantity(), orderUpdateRequest.request());

        return new OrderUpdateResponse(
                order.getOrderId(),
                order.getQuantity(),
                order.getRequest()
        );

    }
    //전체 조회
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderResponse::from).toList();
    }
    //단건 조회
    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ExceptionCode.ORDER_ID_ERROR));
    }

    //delete
    @Transactional
    public void deleteOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow( () -> new BusinessException(ExceptionCode.ORDER_ID_ERROR));

//        if (order.getStatus() == OrderStatus.DELETED) {
//            throw new BusinessException(ExceptionCode.ORDER_ALREADY_DELETED);
//        }
//        else {
//            //가진만큼 재고에 더해야함.
//            productService.plusProduct(order.getProductId(),new PlusRequest(order.getQuantity()));
//            //이후 삭제
//            order.delete();
//        }
        order.delete();
    }

    //rollback
    @Transactional
    public void rollbackDeleteOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow( () -> new BusinessException(ExceptionCode.ORDER_ID_ERROR));

//        if(order.getStatus() == OrderStatus.ORDERED) {
//            throw new BusinessException(ExceptionCode.ORDER_ALREADY_EXIST);
//        }
//        //롤백하면 재고 차감해야 함
//        else {
//            productService.minusProduct(order.getProductId(),new MinusRequest(order.getQuantity()));
//            order.rollbackDelete();
//        }
        order.rollbackDelete();
    }
}
