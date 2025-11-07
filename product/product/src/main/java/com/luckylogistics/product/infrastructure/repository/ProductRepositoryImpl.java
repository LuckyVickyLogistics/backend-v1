package com.luckylogistics.product.infrastructure.repository;

import com.luckylogistics.product.domain.entity.Product;
import com.luckylogistics.product.domain.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@AllArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJPARepository productJPARepository;

    @Override
    public boolean existsByProductName(String productName) {
        return productJPARepository.existsByProductName(productName);
    }

    @Override
    public List<Product> findByProductNameContainingIgnoreCase(String keyword) {
        return  productJPARepository.findByProductNameContainingIgnoreCase(keyword);
    }
}
