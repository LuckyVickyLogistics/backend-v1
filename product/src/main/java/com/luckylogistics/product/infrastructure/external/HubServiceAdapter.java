package com.luckylogistics.product.infrastructure.external;

import com.luckylogistics.product.application.external.HubService;
import com.luckylogistics.product.infrastructure.client.HubDummyClient;
import com.luckylogistics.product.infrastructure.client.HubFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HubServiceAdapter implements HubService {
    //private final HubFeignClient hubFeignClient;
    private final HubDummyClient hubFeignClient;

    @Override
    public void isHubExists(UUID hubId) {
        if(!(hubFeignClient.isHubExists(hubId))) {
            throw new RuntimeException("연결된 허브를 찾을 수 없습니다.");
        }
    }
}
