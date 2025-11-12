package com.luckylogistics.order.infrastructure.client;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.luckylogistics.order.application.dto.MinusRequest;
import com.luckylogistics.order.application.dto.PlusRequest;
import com.luckylogistics.order.common.response.ApiResponse;
import com.luckylogistics.order.infrastructure.client.dto.GetProductClientResponse;

@Component
public class ProductDummyClient {
    public boolean isProductExists(UUID productId) {
        return true;
    }

    public void plusProduct( UUID productId ,PlusRequest plusRequest) {

    }

    public void minusProduct( UUID productId,  MinusRequest minusRequest){

    }

	public ApiResponse<GetProductClientResponse> getProductById(UUID productId) {
		GetProductClientResponse dummy = new GetProductClientResponse(
			productId,
			UUID.randomUUID(),
			UUID.randomUUID(),
			"Dummy Product",
			10000,
			50,
			50,
			"ON_SALE"
		);

		return ApiResponse.success(dummy, "Created Dummy Data");
	}

}