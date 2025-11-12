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

    //단건조회(허브 제한)
    Optional<Product> findByProductIdAndHubId(UUID productId, UUID hubId);

    //단건조회(업체 제한)
    Optional<Product> findByProductIdAndCompanyId(UUID productId, UUID companyId);

    //Keyword 검색 (마스터용)
    List<Product> findByProductNameContainingIgnoreCase(String keyword);

    //전체조회(KeyWord 검색 - 허브 제한)
    List<Product> findByHubIdAndProductNameContainingIgnoreCase(UUID hubId, String keyword);
    List<Product> findByCompanyIdAndProductNameContainingIgnoreCase(UUID companyId, String keyword);


    //전체 조회
    List<Product> findByHubId(UUID hubId);
    List<Product> findByCompanyId(UUID companyId);
}
