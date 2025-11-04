package com.luckylogistics.company.infrastructure.client;

import com.luckylogistics.company.application.service.HubClient;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class HubClientImpl implements HubClient {
    @Override
    public boolean isHubExists(UUID hubId){
        // todo: feignClient
        return false;
    }

}
