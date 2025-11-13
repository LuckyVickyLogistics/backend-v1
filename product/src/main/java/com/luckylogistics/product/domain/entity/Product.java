package com.luckylogistics.product.domain.entity;

import com.luckylogistics.common.infrastructure.exception.BusinessException;
import com.luckylogistics.common.infrastructure.exception.ErrorCode;
import com.luckylogistics.product.domain.vo.Quantity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "p_products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id", nullable = false, updatable = false)
    private UUID productId;
    @Column(name = "product_name", nullable = false)
    private String productName;


    @Column(name = "company_id", nullable = false)
    private UUID companyId;
    @Column(name = "hub_id", nullable = false)
    private UUID hubId;


    @Embedded
    @AttributeOverride(name = "value",
            column = @Column(name = "product_quantity", nullable = false))
    private Quantity quantity;
    @Column(name = "product_total_quantity", nullable = false)
    private int totalQuantity;
    @Column(name = "price", nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(name = "p_status", nullable = false)
    private ProductStatus status;

    //DDD에선 내부에서 모든값을 검증하고 조정한다.

    public static Product create(String productName, UUID companyId, UUID hubId, Quantity quantity, int totalQuantity, int price) {

        if (productName == null || productName.isBlank()) {
            throw new BusinessException(ErrorCode.PRODUCT_NAME_ERROR);
        }
        if (price < 0) {
            throw new BusinessException(ErrorCode.PRODUCT_PRICE_ZERO);
        }
        if (companyId == null) {
            throw new BusinessException(ErrorCode.PRODUCT_COMPANY_ERROR);
        }
        if (hubId == null) {
            throw new BusinessException(ErrorCode.PRODUCT_HUB_ERROR);
        }
        if (totalQuantity < 0) {
            throw new BusinessException(ErrorCode.PRODUCT_QUANTITY_NONZERO);
        }

        if (quantity == null) {
            quantity = new Quantity(0);
        }
        if (quantity.getValue() > totalQuantity) {
            throw new BusinessException(ErrorCode.PRODUCT_TOTAL_EXCEED);
        }

        return Product.builder()
                .productName(productName)
                .companyId(companyId)
                .hubId(hubId)
                .quantity(quantity)
                .totalQuantity(totalQuantity)
                .price(price)
                .status(ProductStatus.ON_SALE)
                .build();
    }

    public void update(String productName, int price, int totalQuantity, Quantity quantity) {
        if (productName == null || productName.isBlank()) {
            throw new BusinessException(ErrorCode.PRODUCT_NAME_UPDATE);
        }
        if (price < 0) {
            throw new BusinessException(ErrorCode.PRODUCT_PRICE_UPDATE);
        }
        if (quantity == null) {
            throw new BusinessException(ErrorCode.PRODUCT_QUANTITY_ZERO_UPDATE);
        }
        if (quantity.getValue() > totalQuantity) {
            throw new BusinessException(ErrorCode.PRODUCT_QUANTITY_TOTAL_UPDATE);
        }
        if (totalQuantity < 0) {
            throw new BusinessException(ErrorCode.PRODUCT_QUANTITY_NONZERO);
        }
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.totalQuantity = totalQuantity;
    }

    public void delete() {
        this.status = ProductStatus.DELETED;
    }

    public void rollbackDelete() {
        this.status = ProductStatus.ON_SALE;
    }

    public void hidden() {this.status = ProductStatus.HIDDEN;}

    public void minusQuantity(int amount) {
        if (this.status == ProductStatus.DELETED) {
            throw new BusinessException(ErrorCode.PRODUCT_QUANTITY_DELETED);
        }

        if (amount > this.totalQuantity) {
            throw new BusinessException(ErrorCode.QUANTITY_AMOUNT_ERROR);
        }

        this.quantity = this.quantity.minus(amount);

        // 재고 0 이면 SOLD_OUT으로 변경
        this.status = ProductStatus.fromQuantity(this.quantity.getValue());
    }

    public void plusQuantity(int amount) {
        if (this.status == ProductStatus.DELETED) {
            throw new BusinessException(ErrorCode.PRODUCT_QUANTITY_DELETED);
        }

        if (amount > this.totalQuantity) {
            throw new BusinessException(ErrorCode.QUANTITY_AMOUNT_ERROR);
        }

        Quantity plusValue = this.quantity.plus(amount);

        if (plusValue.getValue() > this.totalQuantity) {
            throw new BusinessException(ErrorCode.QUANTITY_PLUS_EXCEED);
        }

        //더한 값 적용
        this.quantity = plusValue;

        //Sold Out 의 값에 재고 추가되면 바뀌어야 함
        this.status = ProductStatus.fromQuantity(this.quantity.getValue());


    }

    //주문 -> 재고 차감 -> 0개가 되면 SOLD_OUT 이런식으로 표시
    //주문 -> 재고 차감 -> 실패 (ROLLBACK)
}


