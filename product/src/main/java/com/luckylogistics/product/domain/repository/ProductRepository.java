package com.luckylogistics.product.domain.repository;

import com.luckylogistics.product.domain.entity.Product;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(UUID id);
    List<Product> findAll();
    boolean existsByProductName(String productName);
    //Keyword 검색
    List<Product> findByProductNameContainingIgnoreCase(String keyword);
}
