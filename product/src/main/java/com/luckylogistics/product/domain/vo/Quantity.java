package com.luckylogistics.product.domain.vo;

import com.luckylogistics.common.infrastructure.exception.BusinessException;
import com.luckylogistics.common.infrastructure.exception.ErrorCode;
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
            throw new BusinessException(ErrorCode.PRODUCT_QUANTITY_NONZERO);
        }
        this.value = value;
    }

    public Quantity minus(int amount) {
        if (amount <= 0) {
            throw new BusinessException(ErrorCode.QUANTITY_MINUS_NONZERO);
        }
        int finalQuantity  = this.value - amount;
        if (finalQuantity < 0) {
            throw new BusinessException(ErrorCode.QUANTITY_MINUS_EXCEED);
        }
        return new Quantity(finalQuantity);
    }

    public Quantity plus(int amount) {
        if (amount <= 0) {
            throw new BusinessException(ErrorCode.QUANTITY_PLUS_NONZERO);
        }
        int finalQuantity  = this.value + amount;
        return new Quantity(finalQuantity);

    }

    //재고 확인하는 메서드
    public boolean isZero() {
        return this.value == 0;
    }
    public boolean nonZero() {
        return this.value > 0;
    }


}
