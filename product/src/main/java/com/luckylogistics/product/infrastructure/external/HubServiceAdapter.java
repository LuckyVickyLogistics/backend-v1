package com.luckylogistics.product.infrastructure.external;

import com.luckylogistics.product.infrastructure.client.dto.HubResponse;
import com.luckylogistics.product.application.external.HubService;
import com.luckylogistics.common.infrastructure.response.ApiResponse;
import com.luckylogistics.product.infrastructure.client.HubFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HubServiceAdapter implements HubService {
    private final HubFeignClient hubFeignClient;
    //private final HubDummyClient hubFeignClient;

    //허브 id는 가져올수 있는데 3번 허브 id
    @Override
    public HubResponse getHub(UUID hubId) {
        ApiResponse<HubResponse> response = hubFeignClient.getHub(hubId);
        return response.data();
    }

    // 그 사람이 거기 속해있는가? userId 를 던졌을 때, 그 사람이 그 허브에 속한 사람인가
    // 3번 허브 id를 뱉으면 => 일치 o x
    public HubResponse getHubByUserId(Long userId){
        ApiResponse<HubResponse> response = hubFeignClient.getHubByUserId(userId);
        return response.data();
    }
}
