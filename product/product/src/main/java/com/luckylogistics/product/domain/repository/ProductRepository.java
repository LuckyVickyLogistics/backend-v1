package com.luckylogistics.product.domain.repository;

import com.luckylogistics.product.domain.entity.Product;
import java.util.List;

public interface ProductRepository{
    boolean existsByProductName(String productName);

    //Keyword 검색
    List<Product> findByProductNameContainingIgnoreCase(String keyword);
}
