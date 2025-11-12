package com.luckylogistics.user.application.external;

import java.util.List;
import java.util.UUID;

import com.luckylogistics.user.common.response.ApiResponse;
import com.luckylogistics.user.infrastructure.client.HubResponse;

public interface HubService {

	ApiResponse<HubResponse> getHubById(UUID hubId);

	ApiResponse<List<HubResponse>> getAllHubs();
}
