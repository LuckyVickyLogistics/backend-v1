package com.luckylogistics.product.infrastructure.external;

import com.luckylogistics.product.infrastructure.client.dto.HubResponse;
import com.luckylogistics.product.application.external.HubService;
import com.luckylogistics.product.common.response.ApiResponse;
import com.luckylogistics.product.infrastructure.client.HubFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HubServiceAdapter implements HubService {
    private final HubFeignClient hubFeignClient;
    //private final HubDummyClient hubFeignClient;


    //dummy용
    @Override
    public HubResponse getHub(UUID hubId) {
        ApiResponse<HubResponse> response = hubFeignClient.getHub(hubId);
        return response.data();
    }
}
