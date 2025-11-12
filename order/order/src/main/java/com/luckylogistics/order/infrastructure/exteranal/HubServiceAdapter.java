package com.luckylogistics.order.infrastructure.exteranal;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.luckylogistics.order.application.dto.HubManagerEmailResponse;
import com.luckylogistics.order.application.external.HubService;
import com.luckylogistics.order.common.response.ApiResponse;
import com.luckylogistics.order.infrastructure.HubDummyClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HubServiceAdapter implements HubService {

	// private final HubFeignClient hubFeignClient;
	private final HubDummyClient hubFeignClient;

	@Override
	public HubManagerEmailResponse getHubManagerEmail(UUID hubId) {
		ApiResponse<String> response = hubFeignClient.getHubManager(hubId);
		return HubManagerEmailResponse.of(response.data());
	}

}
