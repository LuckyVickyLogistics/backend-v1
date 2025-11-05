package com.luckylogistics.product.presentation.controller;

import com.luckylogistics.product.application.service.ProductService;
import com.luckylogistics.product.domain.entity.Product;
import com.luckylogistics.product.domain.repository.ProductRepository;
import com.luckylogistics.product.presentation.dto.ProductRequest;
import com.luckylogistics.product.presentation.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse result = productService.createProduct(productRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable(name = "productId") UUID productId,@RequestBody ProductRequest productRequest) {
        ProductResponse result = productService.updateProduct(productId,productRequest);
        return ResponseEntity.ok().build();
    }

    //보류 1106 해결예정
    /**
    @GetMapping
    public ProductResponse read(@RequestParam(required = false) UUID productId){
        if (productId != null) {
            return ProductResponse.from(productService.getProduct(productId));
        }
        return productService.getAllProducts()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }
**/
    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("rollbackProducts/{productId}")
    public ResponseEntity<ProductResponse> rollbackProducts(@PathVariable UUID productId) {
        productService.rollbackDeleteProduct(productId);
        return ResponseEntity.ok().build();
    }

}
