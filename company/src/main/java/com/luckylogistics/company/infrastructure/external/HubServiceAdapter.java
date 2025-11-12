package com.luckylogistics.company.infrastructure.external;

import com.luckylogistics.common.infrastructure.exception.BusinessException;
import com.luckylogistics.common.infrastructure.exception.ErrorCode;
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
        if (!(hubFeignClient.isHubExists(hubId))){
            throw new BusinessException(ErrorCode.HUB_NOT_FOUND);
        };
    }
}
