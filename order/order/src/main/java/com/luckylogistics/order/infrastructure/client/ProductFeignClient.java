package com.luckylogistics.order.infrastructure.client;

import com.luckylogistics.order.application.dto.MinusRequest;
import com.luckylogistics.order.application.dto.PlusRequest;
import com.luckylogistics.order.application.external.ProductService;
import com.luckylogistics.order.common.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "product")
public interface ProductFeignClient {

    @GetMapping("/{productId}")
    ApiResponse<Boolean> isProductIdExists(@PathVariable("productId") UUID productId);

    @PutMapping("/plusProductsQuan/{productId}")
    ApiResponse<Void> plusProduct(@PathVariable("productId") UUID productId , @RequestBody PlusRequest plusRequest);

    @PutMapping("/minusProductsQuan/{productId}")
    ApiResponse<Void> minusProduct(@PathVariable("productId")  UUID productId, @RequestBody MinusRequest minusRequest);
}
