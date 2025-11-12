package com.luckylogistics.order.infrastructure.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.luckylogistics.order.application.dto.MinusRequest;
import com.luckylogistics.order.application.dto.PlusRequest;
import com.luckylogistics.order.common.response.ApiResponse;
import com.luckylogistics.order.infrastructure.client.dto.GetProductClientResponse;

@FeignClient(name = "product", path = "/api/v1/products")
public interface ProductFeignClient {

//    @GetMapping("/{productId}")
//    ApiResponse<Boolean> isProductIdExists(@PathVariable("productId") UUID productId);

    @PutMapping("/plusProductsQuan/{productId}")
    ApiResponse<Void> plusProduct(@PathVariable("productId") UUID productId , @RequestBody PlusRequest plusRequest);

    @PutMapping("/minusProductsQuan/{productId}")
    ApiResponse<Void> minusProduct(@PathVariable("productId")  UUID productId, @RequestBody MinusRequest minusRequest);

	@GetMapping("/{productId}")
	ApiResponse<GetProductClientResponse> getProductById(@PathVariable("productId") UUID productId);

}
