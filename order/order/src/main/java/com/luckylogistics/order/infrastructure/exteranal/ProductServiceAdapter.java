package com.luckylogistics.order.infrastructure.exteranal;

import java.util.UUID;

import com.luckylogistics.order.infrastructure.client.ProductFeignClient;
import org.springframework.stereotype.Component;

import com.luckylogistics.order.application.dto.MinusRequest;
import com.luckylogistics.order.application.dto.PlusRequest;
import com.luckylogistics.order.application.dto.ProductResponse;
import com.luckylogistics.order.application.external.ProductService;
import com.luckylogistics.order.common.exception.BusinessException;
import com.luckylogistics.order.common.exception.ExceptionCode;
import com.luckylogistics.order.common.response.ApiResponse;
import com.luckylogistics.order.infrastructure.client.ProductDummyClient;
import com.luckylogistics.order.infrastructure.client.dto.GetProductClientResponse;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductServiceAdapter implements ProductService {

	 private final ProductFeignClient productFeignClient;
    //private final ProductDummyClient productFeignClient;

	@Override
	public ProductResponse getProductById(UUID productId) {
		try {
			ApiResponse<GetProductClientResponse> response = productFeignClient.getProductById(productId);
			return GetProductClientResponse.of(response.data());
		} catch (FeignException.NotFound e) {
			throw new BusinessException(ExceptionCode.PRODUCT_CANNOT_FIND);
		}
	}

    @Override
    public void isProductIdExists(UUID productId) {
        if(!(productFeignClient.isProductIdExists(productId).data())){
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
