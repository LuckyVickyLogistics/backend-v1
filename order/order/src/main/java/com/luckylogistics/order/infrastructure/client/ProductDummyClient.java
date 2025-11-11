package com.luckylogistics.order.infrastructure.client;

import com.luckylogistics.order.application.dto.MinusRequest;
import com.luckylogistics.order.application.dto.PlusRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.UUID;

@Component
public class ProductDummyClient {
    public boolean isProductExists(UUID productId) {
        return true;
    }

    public void plusProduct( UUID productId ,PlusRequest plusRequest) {

    }

    public void minusProduct( UUID productId,  MinusRequest minusRequest){

    }



}