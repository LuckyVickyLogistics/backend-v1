package com.luckylogistics.product.presentation.controller;

import brave.Response;
import com.luckylogistics.product.application.service.ProductService;
import com.luckylogistics.product.domain.entity.Product;
import com.luckylogistics.product.domain.repository.ProductRepository;
import com.luckylogistics.product.application.dto.ProductRequest;
import com.luckylogistics.product.application.dto.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse result = productService.createProduct(productRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<ProductResponse> updateProduct(@Valid @PathVariable(name = "productId") UUID productId, @RequestBody ProductRequest productRequest) {
        ProductResponse result = productService.updateProduct(productId, productRequest);
        return ResponseEntity.ok().build();
    }

    //단건 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable(name = "productId") UUID productId) {
        return ResponseEntity.ok(ProductResponse.from(productService.getProduct(productId)));
    }

    //전체 조회 및 검색
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(
            @RequestParam(required = false) String productName
    ) {
        List<Product> List;
        if (productName != null && !productName.isBlank()) {
            List = productService.searchProductsByName(productName);
        } else {
            List = productService.getAllProducts();
        }
        List <ProductResponse> finalList = List.stream().map(ProductResponse::from).toList();
        return ResponseEntity.ok(finalList);
    }


    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse> deleteProduct(@Valid @PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("rollbackProducts/{productId}")
    public ResponseEntity<ProductResponse> rollbackProducts(@Valid @PathVariable UUID productId) {
        productService.rollbackDeleteProduct(productId);
        return ResponseEntity.ok().build();
    }

}
