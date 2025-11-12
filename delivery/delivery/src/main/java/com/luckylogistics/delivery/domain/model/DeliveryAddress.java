package com.luckylogistics.delivery.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 배송지 주소 값 객체
 */
@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryAddress {

    private static final int MAX_LENGTH = 500;

    @Column(name = "delivery_address", nullable = false, length = 500)
    private String deliveryAddress;

    private DeliveryAddress(String deliveryAddress) {
        validate(deliveryAddress);
        this.deliveryAddress = deliveryAddress;
    }

    public static DeliveryAddress of(String deliveryAddress) {
        return new DeliveryAddress(deliveryAddress);
    }

    private void validate(String deliveryAddress) {
        if (deliveryAddress == null || deliveryAddress.isBlank()) {
            throw new IllegalArgumentException("배송지 주소는 필수입니다");
        }
        if (deliveryAddress.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("배송지 주소는 %d자 이하여야 합니다", MAX_LENGTH));
        }
    }
}