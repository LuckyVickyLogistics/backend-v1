package com.luckylogistics.order.domain.entity;

import java.util.UUID;

import com.luckylogistics.order.common.exception.BusinessException;
import com.luckylogistics.order.common.exception.ErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name= "order_id", nullable = false, updatable = false)
    private UUID orderId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "delivery_id")
    private UUID deliveryId;

    @Column(name = "supplier_id", nullable = false)
    private UUID supplierId;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "product_id" , nullable = false)
    private UUID productId;

	@Column(name = "delivery_address", nullable = false)
	private String deliveryAddress;

    @Column(name= "request", nullable = false)
    private String request;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    //내부값 검증

    public static Order create(int quantity, UUID supplierId, UUID customerId, UUID productId, String deliveryAddress, String request) {

        if(quantity <= 0) {
            throw new BusinessException(ErrorCode.ORDER_QUANTITY_ERROR);
        }
        if (supplierId == null) {
            throw new BusinessException(ErrorCode.ORDER_SUPPLY_ERROR);
        }
        if (customerId == null) {
            throw new BusinessException(ErrorCode.ORDER_CUSTOMER_ERROR);
        }
        if (productId == null) {
            throw new BusinessException(ErrorCode.ORDER_PRODUCT_ERROR);
        }
        if (request == null) {
            throw new BusinessException(ErrorCode.ORDER_REQUEST_ERROR);
        }

        return Order.builder()
                .quantity(quantity)
                .supplierId(supplierId)
                .customerId(customerId)
                .productId(productId)
				.deliveryAddress(deliveryAddress)
                .request(request)
                .status(OrderStatus.ORDERED)
                .build();
    }

    public void updateDeliveryToOrder(UUID orderId, UUID deliveryId){
        if(deliveryId == null) {
            throw new BusinessException(ErrorCode.ORDER_DELIVERY_ERROR);
        }
        if(orderId == null) {
            throw new BusinessException(ErrorCode.ORDER_DELIVERY_ERROR);
        }
        this.deliveryId = deliveryId;
    }

    public void update(int quantity, String request){
        if (this.status == OrderStatus.DELETED) {
            throw new BusinessException(ErrorCode.ORDER_ALREADY_DELETED);
        }

        if (quantity <= 0) {
            throw new BusinessException(ErrorCode.ORDER_QUANTITY_ERROR);
        }
        if (request == null) {
            throw new BusinessException(ErrorCode.ORDER_REQUEST_ERROR);
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
