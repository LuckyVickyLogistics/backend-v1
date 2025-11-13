package com.luckylogistics.order.infrastructure.exteranal;

import java.util.UUID;

import com.luckylogistics.order.infrastructure.client.HubFeignClient;
import com.luckylogistics.order.infrastructure.client.dto.HubResponse;
import org.springframework.stereotype.Component;

import com.luckylogistics.order.application.dto.HubManagerEmailResponse;
import com.luckylogistics.order.application.external.HubService;
import com.luckylogistics.common.infrastructure.response.ApiResponse;


import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HubServiceAdapter implements HubService {

	 private final HubFeignClient hubFeignClient;
	//private final HubDummyClient hubFeignClient;

	@Override
	public HubManagerEmailResponse getHubManagerEmail(UUID hubId) {
		ApiResponse<String> response = hubFeignClient.getHubManager(hubId);
		return HubManagerEmailResponse.of(response.data());
	}

    @Override
    public HubResponse getHub(UUID hubId) {
        ApiResponse<HubResponse> response = hubFeignClient.getHub(hubId);
        return response.data();
    }

    @Override
    public HubResponse getHubByUserId(Long userId) {
        ApiResponse<HubResponse> response = hubFeignClient.getHubByUserId(userId);
        return response.data();
    }
}
