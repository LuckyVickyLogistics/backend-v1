package com.luckylogistics.order.infrastructure.exteranal;

import java.util.UUID;

import com.luckylogistics.order.infrastructure.client.DeliveryFeignClient;
import org.springframework.stereotype.Component;

import com.luckylogistics.order.application.dto.DeliveryCreateResponse;
import com.luckylogistics.order.application.external.DeliveryService;
import com.luckylogistics.common.infrastructure.response.ApiResponse;
import com.luckylogistics.order.domain.entity.Order;
import com.luckylogistics.order.infrastructure.client.DeliveryDummyClient;
import com.luckylogistics.order.infrastructure.client.dto.CreateDeliveryClientRequest;
import com.luckylogistics.order.infrastructure.client.dto.CreateDeliveryClientResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeliveryServiceAdapter implements DeliveryService {
    private final DeliveryFeignClient deliveryFeignClient;
    //private final DeliveryDummyClient deliveryFeignClient;


//    @Override
//    public void isDeliveryExists(UUID deliveryId) {
//        if(!(deliveryFeignClient.isDelieveryExists(deliveryId))){
//            throw new RuntimeException("배송 연결정보가 존재하지 않습니다.");
//        }
//    }

    @Override
    public boolean getDelivery(UUID deliveryId) {
        return true;
    }

    @Override
	public DeliveryCreateResponse createDelivery(Order order, UUID departureHubId, UUID arrivalHubId, String recipientName, String recipientSlackId) {
		CreateDeliveryClientRequest requestDto = CreateDeliveryClientRequest.from(order, departureHubId, arrivalHubId, recipientName, recipientSlackId);
		ApiResponse<CreateDeliveryClientResponse> response = deliveryFeignClient.createDelivery(requestDto);
		return CreateDeliveryClientResponse.of(response.data());
	}


}
