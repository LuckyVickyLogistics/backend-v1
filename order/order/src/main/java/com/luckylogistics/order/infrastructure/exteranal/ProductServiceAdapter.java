package com.luckylogistics.order.infrastructure.exteranal;

import com.luckylogistics.order.application.dto.MinusRequest;
import com.luckylogistics.order.application.dto.PlusRequest;
import com.luckylogistics.order.application.external.ProductService;
import com.luckylogistics.order.infrastructure.client.ProductDummyClient;
import com.luckylogistics.order.infrastructure.client.ProductFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductServiceAdapter implements ProductService {
   // private final ProductFeignClient productFeignClient;
    private final ProductDummyClient productFeignClient;

    @Override
    public void isProductExists(UUID productId) {
        if(!(productFeignClient.isProductExists(productId))){
            throw new RuntimeException("상품 연결정보가 존재하지 않습니다.");
        }
    }

    @Override
    public void plusProduct(UUID productId, PlusRequest plusRequest) {
        productFeignClient.plusProduct(productId, plusRequest);
    }

    @Override
    public void minusProduct(UUID productId, MinusRequest minusRequest) {
        productFeignClient.minusProduct(productId, minusRequest);
    }
}
