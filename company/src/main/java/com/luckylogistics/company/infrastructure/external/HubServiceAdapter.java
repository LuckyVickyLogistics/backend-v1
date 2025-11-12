package com.luckylogistics.company.infrastructure.external;

import com.luckylogistics.company.application.external.HubService;
import com.luckylogistics.company.infrastructure.client.HubFeignClient;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubServiceAdapter implements HubService {
    private final HubFeignClient hubFeignClient;

    @Override
    public void isHubExists(UUID hubId){
        hubFeignClient.getHub(hubId);
    }
}
