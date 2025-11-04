package com.luckylogistics.product.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Product {

    @Id
    private UUID productId;
    private String productName;
    private UUID companyId;
    private UUID hubId;

    private int quantity;
    private int totalQuantity;
    private int price;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

}
