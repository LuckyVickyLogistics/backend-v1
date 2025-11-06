package com.luckylogistics.product.domain.repository;

import com.luckylogistics.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsByProductName(String productName);

    //Keyword 검색
    List<Product> findByProductNameContainingIgnoreCase(String keyword);
}
