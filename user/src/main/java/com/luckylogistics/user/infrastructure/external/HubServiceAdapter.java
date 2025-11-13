package com.luckylogistics.user.infrastructure.external;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.luckylogistics.user.application.external.HubService;
import com.luckylogistics.user.common.response.ApiResponse;
import com.luckylogistics.user.infrastructure.client.HubFeignClient;
import com.luckylogistics.user.infrastructure.client.HubResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HubServiceAdapter implements HubService {

	private final HubFeignClient hubFeignClient;
	//private final HubDummyClient hubFeignClient;

	@Override
	public ApiResponse<HubResponse> getHubById(UUID hubId) {

		return hubFeignClient.getHubById(hubId);
	}

	@Override
	public ApiResponse<List<HubResponse>> getAllHubs() {
		return hubFeignClient.getAllHubs();
	}
}
