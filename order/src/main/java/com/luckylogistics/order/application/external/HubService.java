package com.luckylogistics.order.application.external;

import java.util.UUID;

import com.luckylogistics.order.application.dto.HubManagerEmailResponse;

public interface HubService {

	HubManagerEmailResponse getHubManagerEmail(UUID hubId);

}
