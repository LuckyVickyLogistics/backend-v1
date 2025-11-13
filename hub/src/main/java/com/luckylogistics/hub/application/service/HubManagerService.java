package com.luckylogistics.hub.application.service;

import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.hub.application.dto.*;

import java.util.List;
import java.util.UUID;

public interface HubManagerService {

    HubManagerCreateResponse createHubManager(HubManagerCreateRequest request, Long userId, UserRole currentUserRole);
    List<HubManagerResponse> getAllHubManagers();
    HubManagerResponse getHubManager(UUID hubId);
    HubResponse getHubByUserId(Long userId);
    HubManagerResponse updateHubManager(UUID hubManagerId, HubManagerUpdateRequest request, Long userId, UserRole currentUserRole);
    void deleteHubManager(UUID hubManagerId, Long userId, UserRole currentUserRole);
}
