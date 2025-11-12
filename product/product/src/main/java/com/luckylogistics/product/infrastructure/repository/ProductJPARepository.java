package com.luckylogistics.product.infrastructure.repository;

import com.luckylogistics.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductJPARepository extends JpaRepository<Product, UUID> {
    boolean existsByProductName(String productName);
    //Keyword 검색
    List<Product> findByProductNameContainingIgnoreCase(String keyword);

    //허브 제한
    Optional<Product> findByProductIdAndHubId(UUID productId, UUID hubId);

    //업체 제한
    Optional<Product> findByProductIdAndCompanyId(UUID productId, UUID companyId);

    //전체조회(KeyWord 검색 - 허브 제한)
    List<Product> findByHubIdAndProductNameContainingIgnoreCase(UUID hubId, String keyword);
    //업체 제한
    List<Product> findByCompanyIdAndProductNameContainingIgnoreCase(UUID companyId, String keyword);


    //전체 조회
    //허브 제한
    List<Product> findByHubId(UUID hubId);
    //업체 제한
    List<Product> findByCompanyId(UUID companyId);
}
