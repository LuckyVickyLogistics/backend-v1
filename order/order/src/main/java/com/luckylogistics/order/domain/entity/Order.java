package com.luckylogistics.order.domain.entity;

import com.luckylogistics.order.common.exception.BusinessException;
import com.luckylogistics.order.common.exception.ExceptionCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "p_orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name= "order_id", nullable = false, updatable = false)
    private UUID orderId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "delivery_id")
    private UUID deliveryId;

    @Column(name = "supplier_id", nullable = false)
    private UUID supplierId;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "product_id" , nullable = false)
    private UUID productId;

    @Column(name= "request", nullable = false)
    private String request;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    //내부값 검증

    public static Order create(int quantity, UUID supplierId, UUID customerId, UUID productId, String request) {

        if(quantity <= 0) {
            throw new BusinessException(ExceptionCode.ORDER_QUANTITY_ERROR);
        }
        if (supplierId == null) {
            throw new BusinessException(ExceptionCode.ORDER_SUPPLY_ERROR);
        }
        if (customerId == null) {
            throw new BusinessException(ExceptionCode.ORDER_CUSTOMER_ERROR);
        }
        if (productId == null) {
            throw new BusinessException(ExceptionCode.ORDER_PRODUCT_ERROR);
        }
        if (request == null) {
            throw new BusinessException(ExceptionCode.ORDER_REQUEST_ERROR);
        }

        return Order.builder()
                .quantity(quantity)
                .supplierId(supplierId)
                .customerId(customerId)
                .productId(productId)
                .request(request)
                .status(OrderStatus.ORDERED)
                .build();
    }

    public void updateDeliveryToOrder(UUID orderId, UUID deliveryId){
        if(deliveryId == null) {
            throw new BusinessException(ExceptionCode.ORDER_DELIVERY_ERROR);
        }
        if(orderId == null) {
            throw new BusinessException(ExceptionCode.ORDER_DELIVERY_ERROR);
        }
        this.deliveryId = deliveryId;
    }

    public void update(int quantity, String request){
        if (quantity <= 0) {
            throw new BusinessException(ExceptionCode.ORDER_QUANTITY_ERROR);
        }
        if (request == null) {
            throw new BusinessException(ExceptionCode.ORDER_REQUEST_ERROR);
        }
        this.quantity = quantity;
        this.request = request;
    }

    public void delete() {
        this.status = OrderStatus.DELETED;
    }

    public void rollbackDelete() {
        this.status = OrderStatus.ORDERED;
    }
}
