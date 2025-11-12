package com.luckylogistics.product.presentation.controller;

import brave.Response;
import com.luckylogistics.product.application.dto.MinusRequest;
import com.luckylogistics.product.application.dto.PlusRequest;
import com.luckylogistics.product.application.service.ProductService;
import com.luckylogistics.product.application.dto.ProductRequest;
import com.luckylogistics.product.application.dto.ProductResponse;
import com.luckylogistics.product.common.enums.UserRole;
import com.luckylogistics.product.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @Valid @RequestBody ProductRequest productRequest,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole) {
        ProductResponse result = productService.createProduct(productRequest,currentUserId,currentUserRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(result,"상품이 생성되었습니다."));
    }

    @Operation(summary = "상품 수정", description = "상품 수정 api")
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @Valid @PathVariable(name = "productId") UUID productId,
            @RequestBody ProductRequest productRequest,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole) {
        ProductResponse result = productService.updateProduct(productId, productRequest,currentUserId,currentUserRole);
        return ResponseEntity.ok(ApiResponse.success(result,"상품 수정이 완료되었습니다."));
    }

    @Operation(summary = "상품 단건 조회", description = "단 한건의 상품 정보를 조회합니다.")
    //단건 조회
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(
            @PathVariable(name = "productId") UUID productId,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole) {
        ProductResponse result = ProductResponse.from(productService.getProduct(productId,currentUserId,currentUserRole));
        return ResponseEntity.ok(ApiResponse.success(result,"상품 단건 조회 결과입니다."));
    }
//    //있는지 확인 (FeignClient용)
//    @Operation(summary = "FeignClient용 조회 메서드 ", description = "상품이 존재하는지 확인합니다.")
//    @GetMapping("/{productId}")
//    public ResponseEntity<ApiResponse<Boolean>> isProductIdExists(@PathVariable(name = "productId") UUID productId) {
//        Boolean result = productService.checkProduct(productId);
//        return ResponseEntity.ok(ApiResponse.success(result,"FeignClient : 상품 정상 조회 되었습니다. "));
//    }


    //전체 조회 및 검색
    @Operation(summary = "상품 조회(검색)", description = "전체 상품을 조회하거나 특정 키워드 연관 상품을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts(
            @RequestParam(required = false) String productName,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole
    ) {
        List<ProductResponse> list = productService.allOrSearchProducts(productName,currentUserId,currentUserRole);
       return ResponseEntity.ok(ApiResponse.success(list,"상품 조회 결과입니다."));

    }

    // 삭제
    @Operation(summary = "상품 정보 삭제", description = "상품 삭제 api")
    @PutMapping("/deleteProducts/{productId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteProduct(
            @Valid @PathVariable UUID productId,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole) {
        productService.deleteProduct(productId,currentUserId,currentUserRole);
        return ResponseEntity.ok(ApiResponse.success(null,"상품이 삭제되었습니다."));
    }
    //삭제 롤백
    @Operation(summary = "상품 삭제정보 롤백 ", description = "상품 삭제정보 롤백")
    @PutMapping("/rollbackProducts/{productId}")
    public ResponseEntity<ApiResponse<Boolean>> rollbackProducts(
            @Valid @PathVariable UUID productId,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole) {
        productService.rollbackDeleteProduct(productId,currentUserId,currentUserRole);
        return ResponseEntity.ok(ApiResponse.success(null,"상품이 삭제가 롤백되었습니다."));
    }

    //비활성
    @Operation(summary = "상품 정보 숨기기 ", description = "상품 삭제정보 숨기기")
    @PutMapping("/hiddenProducts/{productId}")
    public ResponseEntity<ApiResponse<Boolean>> hiddenProducts(
            @Valid @PathVariable UUID productId,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole) {
        productService.hiddenProducts(productId,currentUserId,currentUserRole);
        return ResponseEntity.ok(ApiResponse.success(null,"상품이 비활성화 처리되었습니다."));

    }


    //재고 감소
    @Operation(summary = "해당 상품 재고 감소", description = "주문이 들어왔을 때 해당 상품의 재고를 n만큼 감소시킨다")
    @PutMapping("/minusProductsQuan/{productId}")
    public ResponseEntity<ApiResponse<Boolean>> minusProducts(
            @Valid @PathVariable UUID productId,
            @RequestBody MinusRequest request,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole) {
        productService.minusProducts(productId,request,currentUserId,currentUserRole);
        return ResponseEntity.ok(ApiResponse.success(null,"상품이 차감되었습니다."));

    }

    //재고 추가
    @Operation(summary = "해당 상품 재고 추가", description = "주문이 들어왔을 때 해당 상품의 재고를 n만큼 추가시킨다")
    @PutMapping("/plusProductsQuan/{productId}")
    public ResponseEntity<ApiResponse<Boolean>> plusProducts(
            @Valid @PathVariable UUID productId,
            @RequestBody PlusRequest request,
            @RequestHeader("X-User-Id") Long currentUserId,
            @RequestHeader("X-User-Role") UserRole currentUserRole) {
        productService.plusProducts(productId,request,currentUserId,currentUserRole);
        return ResponseEntity.ok(ApiResponse.success(null,"상품 재고가 늘어났습니다.."));
    }


}
