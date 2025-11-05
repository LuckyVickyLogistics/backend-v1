package com.luckylogistics.product.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Quantity {
    private int value;

    public Quantity(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("수량은 0 이상이어야 합니다.");
        }
        this.value = value;
    }

    public Quantity minus(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("출고할 재고를 1개 이상 선택해야 합니다");
        }
        int totalAmount = this.value - amount;
        if (totalAmount < 0) {
            throw new IllegalArgumentException("상품 재고가 부족합니다.");
        }
        return new Quantity(totalAmount);
    }

    //품절 대비로 넣음
    public boolean isZero() {
        return this.value == 0;
    }


}
