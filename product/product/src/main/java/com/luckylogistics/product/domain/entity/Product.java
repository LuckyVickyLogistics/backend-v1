package com.luckylogistics.product.domain.entity;

import com.luckylogistics.product.domain.vo.Quantity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    public void update(String productName, int price, int totalQuantity, Quantity quantity) {
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
        if (totalQuantity < 0) {
            throw new IllegalArgumentException("총 수량은 0 이상이어야 합니다.");
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
            throw new IllegalArgumentException("삭제된 상품의 재고는 차감될 수 없습니다.");
        }

        if (amount > this.totalQuantity) {
            throw new IllegalArgumentException("주문 수량은 총 수량을 넘길 수 없습니다");
        }

        this.quantity = this.quantity.minus(amount);

        // 재고 0 이면 SOLD_OUT으로 변경
        this.status = ProductStatus.fromQuantity(this.quantity.getValue());
    }

    public void plusQuantity(int amount) {
        if (this.status == ProductStatus.DELETED) {
            throw new IllegalArgumentException("삭제된 상품의 재고는 추가할 수 없습니다.");
        }

        if (amount > this.totalQuantity) {
            throw new IllegalArgumentException("주문 추가는 총 수량을 넘길 수 없습니다");
        }

        Quantity plusValue = this.quantity.plus(amount);

        if (plusValue.getValue() > this.totalQuantity) {
            throw new IllegalArgumentException("추가한 수량의 합이 총 수량을 초과합니다");
        }

        //더한 값 적용
        this.quantity = plusValue;

        //Sold Out 의 값에 재고 추가되면 바뀌어야 함
        this.status = ProductStatus.fromQuantity(this.quantity.getValue());


    }

    //주문 -> 재고 차감 -> 0개가 되면 SOLD_OUT 이런식으로 표시
    //주문 -> 재고 차감 -> 실패 (ROLLBACK)
}


