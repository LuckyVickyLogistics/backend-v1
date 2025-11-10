package com.luckylogistics.order.infrastructure.exteranal;

import com.luckylogistics.order.application.external.DeliveryService;
import com.luckylogistics.order.infrastructure.client.DeliveryDummyClient;
import com.luckylogistics.order.infrastructure.client.DeliveryFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeliveryServiceAdapter implements DeliveryService {
    //private final DeliveryFeignClient deliveryFeignClient;
    private final DeliveryDummyClient deliveryFeignClient;


    @Override
    public void isDeliveryExists(UUID deliveryId) {
        if(!(deliveryFeignClient.isDelieveryExists(deliveryId))){
            throw new RuntimeException("배송 연결정보가 존재하지 않습니다.");
        }
    }
}
