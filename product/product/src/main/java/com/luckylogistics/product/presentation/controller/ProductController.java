package com.luckylogistics.product.presentation.controller;

import com.luckylogistics.product.application.service.ProductService;
import com.luckylogistics.product.application.dto.ProductRequest;
import com.luckylogistics.product.application.dto.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Tag(name = "상품 API" , description = "상품 관련 API입니다.")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "상품 생성", description = "상품 생성 api")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse result = productService.createProduct(productRequest);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "상품 수정", description = "상품 수정 api")
    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@Valid @PathVariable(name = "productId") UUID productId, @RequestBody ProductRequest productRequest) {
        ProductResponse result = productService.updateProduct(productId, productRequest);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "상품 단건 조회", description = "단 한건의 상품 정보를 조회합니다.")
    //단건 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable(name = "productId") UUID productId) {
        ProductResponse result = ProductResponse.from(productService.getProduct(productId));
        return ResponseEntity.ok(result);
    }

    //전체 조회 및 검색
    @Operation(summary = "상품 조회(검색)", description = "전체 상품을 조회하거나 특정 키워드 연관 상품을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(
            @RequestParam(required = false) String productName
    ) {
        List<ProductResponse> list = productService.allOrSearchProducts(productName);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "상품 정보 삭제", description = "상품 삭제 api")
    @PatchMapping("/{productId}")
    public ResponseEntity<Boolean> deleteProduct(@Valid @PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(true);
    }
    @Operation(summary = "상품 삭제정보 롤백 ", description = "상품 삭제정보 롤백")
    @PatchMapping("rollbackProducts/{productId}")
    public ResponseEntity<Boolean> rollbackProducts(@Valid @PathVariable UUID productId) {
        productService.rollbackDeleteProduct(productId);
        return ResponseEntity.ok(true);
    }

}
