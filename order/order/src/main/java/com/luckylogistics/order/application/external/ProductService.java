package com.luckylogistics.order.application.external;

import com.luckylogistics.order.application.dto.MinusRequest;
import com.luckylogistics.order.application.dto.PlusRequest;

import java.util.UUID;

public interface ProductService {
    void isProductExists(UUID productId);
    void plusProduct(UUID productId, PlusRequest plusRequest);
    void minusProduct(UUID productId, MinusRequest minusRequest);
}
