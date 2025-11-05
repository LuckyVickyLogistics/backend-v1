package com.luckylogistics.product.presentation.controller;

import com.luckylogistics.product.application.service.ProductService;
import com.luckylogistics.product.domain.entity.Product;
import com.luckylogistics.product.domain.repository.ProductRepository;
import com.luckylogistics.product.presentation.dto.ProductRequest;
import com.luckylogistics.product.presentation.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;

    @PostMapping
    public ProductResponse create(@RequestBody ProductRequest request) {
        Product product = productService.createProduct(
                request.productName(),
                request.companyId(),
                request.hubId(),
                request.price(),
                request.totalQuantity(),
                request.initialQuantity()
        );
        return ProductResponse.from(product);
    }
}
