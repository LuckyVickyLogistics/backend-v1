package com.luckylogistics.product.application.external;

import com.luckylogistics.product.infrastructure.client.dto.HubResponse;

import java.util.UUID;

public interface HubService {
   // void isHubExists(UUID hubId);

     HubResponse getHub(UUID hubId);
}
