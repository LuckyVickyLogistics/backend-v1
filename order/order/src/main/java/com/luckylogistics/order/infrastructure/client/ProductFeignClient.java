package com.luckylogistics.order.infrastructure.client;

import com.luckylogistics.order.application.dto.MinusRequest;
import com.luckylogistics.order.application.dto.PlusRequest;
import com.luckylogistics.order.application.external.ProductService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "product")
public interface ProductFeignClient {

    @GetMapping("/{productId}")
    boolean isProductExists(@PathVariable("productId") UUID productId);

    @PatchMapping("/plusProductsQuan/{productId}")
    void plusProduct(@PathVariable("productId") UUID productId , @RequestBody PlusRequest plusRequest);

    @PatchMapping("/minusProductsQuan/{productId}")
    void minusProduct(@PathVariable("productId")  UUID productId, @RequestBody MinusRequest minusRequest);
}
