package com.luckylogistics.order.application.external;

import java.util.UUID;

import com.luckylogistics.order.application.dto.MinusRequest;
import com.luckylogistics.order.application.dto.PlusRequest;
import com.luckylogistics.order.application.dto.ProductResponse;

public interface ProductService {
    void isProductIdExists(UUID productId);
    void plusProduct(UUID productId, PlusRequest plusRequest);
    void minusProduct(UUID productId, MinusRequest minusRequest);
    void isProductExists(UUID productId);

	ProductResponse getProductById(UUID productId);
}
