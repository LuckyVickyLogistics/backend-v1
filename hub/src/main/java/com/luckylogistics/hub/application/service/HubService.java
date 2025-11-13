package com.luckylogistics.hub.application.service;

import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.hub.application.dto.*;

import java.util.List;
import java.util.UUID;

public interface HubService {

    HubCreateResponse createHub(HubCreateRequest request, Long userId, UserRole currentUserRole);

    List<HubResponse> getAllHubs();

    HubResponse getHub(UUID hubId);

    HubResponse updateHub(UUID hubId, HubUpdateRequest request, Long userId,  UserRole currentUserRole);

    void deleteHub(UUID hubId, Long userId, UserRole currentUserRole);
}
