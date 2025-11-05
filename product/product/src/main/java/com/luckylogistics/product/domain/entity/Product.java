package com.luckylogistics.product.domain.entity;

import com.luckylogistics.product.infrastructure.model.BaseEntity;
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
            throw new IllegalArgumentException("상품 이름은 빌 수 없습니다.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("상품 가격은 0원 이상이어야 합니다.");
        }
        if (companyId == null) {
            throw new IllegalArgumentException("업체 ID는 필수값입니다.");
        }
        if (hubId == null) {
            throw new IllegalArgumentException("허브 ID는 필수값입니다.");
        }
        if (totalQuantity < 0) {
            throw new IllegalArgumentException("총 수량은 0 이상이어야 합니다.");
        }

        if (quantity == null) {
            quantity = new Quantity(0);
        }
        if (quantity.getValue() > totalQuantity) {
            throw new IllegalArgumentException("초기 재고가 총 수량을 초과했습니다.");
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

    public void update(String productName, int price, int totalQuantity, Quantity quantity, ProductStatus status) {
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("수정할 상품의 이름은 빌 수 없습니다.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("수정할 상품 가격은 0원 이상이어야 합니다.");
        }
        if (quantity == null) {
            throw new IllegalArgumentException("수정할 수량은 0 이상의 숫자여야 합니다.");
        }
        if (quantity.getValue() > totalQuantity) {
            throw new IllegalArgumentException("수정한 재고 수가 총 수량을 초과했습니다.");
        }
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public void delete() {
        this.status = ProductStatus.DELETED;
    }

    public void rollbackDelete() {
        this.status = ProductStatus.ON_SALE;
    }

    public void minusQuantity(int amount) {
        if (this.status == ProductStatus.DELETED) {
            throw new IllegalArgumentException("삭제된 상품의 재고는 차감될 수 없습니다.");
        }
        this.quantity = this.quantity.minus(amount);
        //0이면 SOLD_OUT 으로 바뀌게 조작
        this.status = ProductStatus.fromQuantity(this.quantity.getValue());
    }


}


