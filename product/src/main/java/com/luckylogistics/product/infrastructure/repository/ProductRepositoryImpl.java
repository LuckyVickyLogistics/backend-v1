package com.luckylogistics.product.infrastructure.repository;

import com.luckylogistics.product.domain.entity.Product;
import com.luckylogistics.product.domain.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJPARepository productJPARepository;

    @Override
    public Product save(Product product) {
        return productJPARepository.save(product);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productJPARepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productJPARepository.findAll();
    }

    @Override
    public boolean existsByProductName(String productName) {
        return productJPARepository.existsByProductName(productName);
    }

    @Override
    public List<Product> findByProductNameContainingIgnoreCase(String keyword) {
        return  productJPARepository.findByProductNameContainingIgnoreCase(keyword);
    }

    @Override
    public Optional<Product> findByProductIdAndCompanyId(UUID productId, UUID companyId) {
        return productJPARepository.findByProductIdAndCompanyId(productId, companyId);
    }

    @Override
    public Optional<Product> findByProductIdAndHubId(UUID productId, UUID hubId) {
        return productJPARepository.findByProductIdAndHubId(productId, hubId);
    }

    @Override
    public List<Product> findByHubIdAndProductNameContainingIgnoreCase(UUID hubId, String keyword) {
        return productJPARepository.findByHubIdAndProductNameContainingIgnoreCase(hubId, keyword);
    }

    @Override
    public List<Product> findByCompanyIdAndProductNameContainingIgnoreCase(UUID companyId, String keyword) {
        return productJPARepository.findByCompanyIdAndProductNameContainingIgnoreCase(companyId, keyword);
    }

    @Override
    public List<Product> findByHubId(UUID hubId) {
        return productJPARepository.findByHubId(hubId);
    }

    @Override
    public List<Product> findByCompanyId(UUID companyId) {
        return productJPARepository.findByCompanyId(companyId);
    }
}
