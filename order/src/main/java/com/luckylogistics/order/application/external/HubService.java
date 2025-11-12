package com.luckylogistics.order.application.external;

import java.util.UUID;

import com.luckylogistics.order.application.dto.HubManagerEmailResponse;
import com.luckylogistics.order.infrastructure.client.dto.HubResponse;

public interface HubService {

	HubManagerEmailResponse getHubManagerEmail(UUID hubId);
    HubResponse getHub(UUID hubId);
    HubResponse getHubByUserId(Long userId);
}
